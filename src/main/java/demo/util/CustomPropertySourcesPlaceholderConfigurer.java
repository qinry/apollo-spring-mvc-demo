package demo.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionVisitor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.*;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringValueResolver;

import java.io.IOException;
import java.util.*;

public class CustomPropertySourcesPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer {

    private BeanFactory beanFactory;

    private String beanName;

    private Environment environment;

    private MutablePropertySources propertySources;

    private PropertySources appliedPropertySources;

    @Override
    public void setPropertySources(PropertySources propertySources) {
        this.propertySources = new MutablePropertySources(propertySources);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (this.propertySources == null) {
            this.propertySources = new MutablePropertySources();
            if (this.environment != null) {
                ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) this.environment;
                MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
                Iterator<PropertySource<?>> iterator =
                        propertySources.iterator();
                List<PropertySource<?>> temp = new ArrayList<>();
                while (iterator.hasNext()) {
                    PropertySource<?> next = iterator.next();
                    temp.add(new PropertySource<PropertySource<?>>(next.getName(), next) {
                        @Override
                        public Object getProperty(String name) {
                            Object value = this.source.getProperty(name);
                            if (value instanceof String) {
                                return CipherUtil.decryptProperty(name, (String) value);
                            } else {
                                return  value;
                            }
                        }
                    });
                }
                temp.forEach(propertySources::addFirst);
                this.propertySources.addLast(
                        new PropertySource<Environment>(ENVIRONMENT_PROPERTIES_PROPERTY_SOURCE_NAME, configurableEnvironment) {
                            @Override
                            public String getProperty(String key) {
                                return this.source.getProperty(key);
                            }
                        }
                );
            }
            try {
                Properties localProperties = mergeProperties();
                Enumeration<?> enumeration = localProperties.propertyNames();
                while (enumeration.hasMoreElements()) {
                    String name = (String) enumeration.nextElement();
                    String value = localProperties.getProperty(name);
                    String resolved = CipherUtil.decryptProperty(name, value);
                    if (!ObjectUtils.nullSafeEquals(value, resolved)) {
                        localProperties.setProperty(name, value);
                    }
                }
                PropertySource<?> localPropertySource =
                        new PropertiesPropertySource(LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME, localProperties);
                if (this.localOverride) {
                    this.propertySources.addFirst(localPropertySource);
                }
                else {
                    this.propertySources.addLast(localPropertySource);
                }
            }
            catch (IOException ex) {
                throw new BeanInitializationException("Could not load properties", ex);
            }
        }
        CustomPropertySourcesPropertyResolver propertyResolver = new CustomPropertySourcesPropertyResolver(this.propertySources);

        processProperties(beanFactory, propertyResolver);
        this.appliedPropertySources = this.propertySources;
    }

    @Override
    public PropertySources getAppliedPropertySources() throws IllegalStateException {
        Assert.state(this.appliedPropertySources != null, "PropertySources have not yet been applied");
        return this.appliedPropertySources;
    }
}

package demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePropertySource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static demo.config.EncryptConstant.ENCRYPT_PROPERTY_SOURCE_NAME;

@Slf4j
public class EncryptPropertySourceProcessor implements BeanFactoryPostProcessor, EnvironmentAware, PriorityOrdered {

    private ConfigurableEnvironment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (this.environment.getPropertySources().contains(ENCRYPT_PROPERTY_SOURCE_NAME)) {
            return;
        }
        // 将原有PropertySource用EncryptPropertySource包装后加进入新的CompositePropertySource
        CompositePropertySource composite = new CompositePropertySource(ENCRYPT_PROPERTY_SOURCE_NAME);
        MutablePropertySources propertySources = environment.getPropertySources();
        Iterator<PropertySource<?>> iterator = propertySources.iterator();
        while (iterator.hasNext()) {
            PropertySource<?> propertySource = iterator.next();
            composite.addPropertySource(new EncryptPropertySource(propertySource.getName(), propertySource));
        }
        // 默认将规定好路径的配置文件加载为EncryptPropertySource装进CompositePropertySource
        // 如需使用规定路径外的配置文件，请使用@PropertySource进行额外加入属性即可
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            List<Resource> resourceList = new ArrayList<>();
            List<String> locationPatterns = new ArrayList<>(Arrays.asList("classpath*:application*.properties"));
            for (String locationPattern : locationPatterns) {
                Resource[] resources = pathMatchingResourcePatternResolver.getResources(locationPattern);
                resourceList.addAll(Arrays.asList(resources));
            }
            List<EncodedResource> encodedResourceList = resourceList.stream().map(EncodedResource::new).collect(Collectors.toList());

            List<ResourcePropertySource> resourcePropertySourceList = new ArrayList<>();
            for (EncodedResource encodedResource : encodedResourceList) {
                resourcePropertySourceList.add(new ResourcePropertySource(encodedResource));
            }
            for (ResourcePropertySource resourcePropertySource : resourcePropertySourceList) {
                composite.addPropertySource(new EncryptPropertySource(resourcePropertySource.getName(), resourcePropertySource));
            }
            // CompositePropertySource加进Environment
            environment.getPropertySources().addFirst(composite);
        } catch (Exception e) {
            log.error("Exception!", e);
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }

    @Override
    public int getOrder() {
        return -1;
    }
}

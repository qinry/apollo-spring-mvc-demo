package demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class EncryptPropertySourceProcessor implements BeanFactoryPostProcessor, EnvironmentAware, PriorityOrdered {

    private ConfigurableEnvironment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            List<PropertySource<?>> list = new ArrayList<>();
            MutablePropertySources propertySources = this.environment.getPropertySources();
            Iterator<PropertySource<?>> iterator = propertySources.iterator();
            while (iterator.hasNext()) {
                PropertySource<?> propertySource = iterator.next();
                String name = propertySource.getName();

                if (ObjectUtils.nullSafeEquals(name, "ApolloPropertySources")) {
                    list.add(new EncryptPropertySource(propertySource.getName(), propertySource));
                }
                if (propertySource instanceof MapPropertySource) {
                    list.add(new EncryptPropertySource(propertySource.getName(), propertySource));
                }
            }
            // 将包装的PropertySource在Environment中进行替换
            for (PropertySource<?> propertySource : list) {
                propertySources.replace(propertySource.getName(), propertySource);
            }
            // 默认将规定好路径的配置文件加载为EncryptPropertySource装进CompositePropertySource
            // 如需使用规定路径外的配置文件，请使用@PropertySource进行额外加入属性即可
            PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
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
                propertySources.addLast(new EncryptPropertySource(resourcePropertySource.getName(), resourcePropertySource));
            }
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

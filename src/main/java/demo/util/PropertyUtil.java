package demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.util.ClassUtils;

import java.beans.Introspector;

@Slf4j
public class PropertyUtil {

    private static PropertySourcesPropertyResolver propertySourcesPropertyResolver;

    static {
        try {
            String className = PropertySourcesPlaceholderConfigurer.class.getName();
            String shortClassName = ClassUtils.getShortName(className);
            String beanName = Introspector.decapitalize(shortClassName);

            PropertySourcesPlaceholderConfigurer bean = null;
            boolean containsBean = SpringUtil.containsBean(beanName);
            if (containsBean) {
                bean = SpringUtil.getBean(beanName, PropertySourcesPlaceholderConfigurer.class);
            } else {
                containsBean = SpringUtil.containsBean(className);
                if (containsBean) {
                    bean = SpringUtil.getBean(className, PropertySourcesPlaceholderConfigurer.class);
                }
            }
            if (bean != null) {
                propertySourcesPropertyResolver = new PropertySourcesPropertyResolver(bean.getAppliedPropertySources());
            }

        } catch (Exception e) {
            log.error("Exception!", e);
        }
    }

    public static String getProperty(String propertyName) {
        if (propertySourcesPropertyResolver == null) {
            return null;
        }
        return propertySourcesPropertyResolver.getProperty(propertyName);
    }

    public static String getProperty(String propertyName, String defaultValue) {
        if (propertySourcesPropertyResolver == null) {
            return null;
        }
        return propertySourcesPropertyResolver.getProperty(propertyName, defaultValue);
    }

    public static <T> T getProperty(String key, Class<T> targetType) {
        if (propertySourcesPropertyResolver == null) {
            return null;
        }
        return propertySourcesPropertyResolver.getProperty(key, targetType);

    }

    public static <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
        if (propertySourcesPropertyResolver == null) {
            return null;
        }
        return propertySourcesPropertyResolver.getProperty(key, targetType, defaultValue);

    }

    public static String getRequiredProperty(String key) throws IllegalStateException {
        if (propertySourcesPropertyResolver == null) {
            return null;
        }
        return propertySourcesPropertyResolver.getRequiredProperty(key);
    }

    public static <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException {
        if (propertySourcesPropertyResolver == null) {
            return null;
        }
        return propertySourcesPropertyResolver.getRequiredProperty(key, targetType);
    }

    public static String resolvePlaceholders(String text) {
        if (propertySourcesPropertyResolver == null) {
            return null;
        }
        return propertySourcesPropertyResolver.resolvePlaceholders(text);
    }

    public static String resolveRequiredPlaceholders(String text) throws IllegalArgumentException {
        if (propertySourcesPropertyResolver == null) {
            return null;
        }
        return propertySourcesPropertyResolver.resolveRequiredPlaceholders(text);
    }
}

package demo.util;

import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.PropertySourcesPropertyResolver;

public class PropertyUtil {

    private static PropertySourcesPropertyResolver propertySourcesPropertyResolver
            = new PropertySourcesPropertyResolver(
                    SpringUtil.getBean("propertySourcesPlaceholderConfigurer", PropertySourcesPlaceholderConfigurer.class).getAppliedPropertySources());

    public static String getProperty(String propertyName) {
        return propertySourcesPropertyResolver.getProperty(propertyName);
    }

    public static String getProperty(String propertyName, String defaultValue) {
        return propertySourcesPropertyResolver.getProperty(propertyName, defaultValue);
    }

    public static <T> T getProperty(String key, Class<T> targetType) {
        return propertySourcesPropertyResolver.getProperty(key, targetType);

    }

    public static <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
        return propertySourcesPropertyResolver.getProperty(key, targetType, defaultValue);

    }

    public static String getRequiredProperty(String key) throws IllegalStateException {
        return propertySourcesPropertyResolver.getRequiredProperty(key);
    }

    public static <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException {
        return propertySourcesPropertyResolver.getRequiredProperty(key, targetType);
    }

    public static String resolvePlaceholders(String text) {
        return propertySourcesPropertyResolver.resolvePlaceholders(text);
    }

    public static String resolveRequiredPlaceholders(String text) throws IllegalArgumentException {
        return propertySourcesPropertyResolver.resolveRequiredPlaceholders(text);
    }
}

package demo.util;

public class PropertyUtil {

    private static CustomPropertySourcesPropertyResolver customPropertySourcesPropertyResolver
            = new CustomPropertySourcesPropertyResolver(
                    SpringUtil.getBean(CustomPropertySourcesPlaceholderConfigurer.class).getAppliedPropertySources());

    public static String getProperty(String propertyName) {
        return customPropertySourcesPropertyResolver.getProperty(propertyName);
    }

    public static String getProperty(String propertyName, String defaultValue) {
        return customPropertySourcesPropertyResolver.getProperty(propertyName, defaultValue);
    }

    public static <T> T getProperty(String key, Class<T> targetType) {
        return customPropertySourcesPropertyResolver.getProperty(key, targetType);

    }

    public static <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
        return customPropertySourcesPropertyResolver.getProperty(key, targetType, defaultValue);

    }

    public static String getRequiredProperty(String key) throws IllegalStateException {
        return customPropertySourcesPropertyResolver.getRequiredProperty(key);
    }

    public static <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException {
        return customPropertySourcesPropertyResolver.getRequiredProperty(key, targetType);
    }

    public static String resolvePlaceholders(String text) {
        return customPropertySourcesPropertyResolver.resolvePlaceholders(text);
    }

    public static String resolveRequiredPlaceholders(String text) throws IllegalArgumentException {
        return customPropertySourcesPropertyResolver.resolveRequiredPlaceholders(text);
    }
}

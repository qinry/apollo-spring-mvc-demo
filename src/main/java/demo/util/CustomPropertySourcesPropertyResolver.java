package demo.util;

import org.springframework.core.env.PropertySources;
import org.springframework.core.env.PropertySourcesPropertyResolver;

public class CustomPropertySourcesPropertyResolver extends PropertySourcesPropertyResolver {

    public CustomPropertySourcesPropertyResolver(PropertySources propertySources) {
        super(propertySources);
    }

    @Override
    protected <T> T getProperty(String key, Class<T> targetValueType, boolean resolveNestedPlaceholders) {
        Object value = super.getProperty(key, targetValueType, resolveNestedPlaceholders);
        if (value != null) {
            if (value instanceof String) {
                value = CipherUtil.decryptProperty(key, (String) value);
            }
        }
        return (T)value;
    }

    @Override
    public String resolvePlaceholders(String text) {
        String value = super.resolvePlaceholders(text);
        if (value != null) {
            value = CipherUtil.decryptProperty(text, (String) value);
        }
        return value;
    }

    @Override
    public String resolveRequiredPlaceholders(String text) throws IllegalArgumentException {
        String value = super.resolveRequiredPlaceholders(text);
        if (value != null) {
            value = CipherUtil.decryptProperty(text, (String) value);
        }
        return value;
    }
}

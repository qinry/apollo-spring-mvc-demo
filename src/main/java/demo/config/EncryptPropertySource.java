package demo.config;

import demo.util.CipherUtil;
import org.springframework.core.env.PropertySource;

public class EncryptPropertySource extends PropertySource<PropertySource<?>> {

    public EncryptPropertySource(String name, PropertySource<?> source) {
        super(name, source);
    }

    public EncryptPropertySource(String name) {
        super(name);
    }

    public EncryptPropertySource(PropertySource<?> source) {
        super("encryptPropertySource", source);
    }

    @Override
    public Object getProperty(String name) {
        Object propertyValue = this.source.getProperty(name);
        if (propertyValue instanceof String) {
            return CipherUtil.decryptProperty(name, (String) propertyValue);
        }
        return propertyValue;
    }
}

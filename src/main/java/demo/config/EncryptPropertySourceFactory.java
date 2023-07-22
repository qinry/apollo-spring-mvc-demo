package demo.config;

import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;

public class EncryptPropertySourceFactory extends DefaultPropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        PropertySource<?> propertySource = super.createPropertySource(name, resource);
        return new EncryptPropertySource(propertySource.getName(), propertySource);
    }
}

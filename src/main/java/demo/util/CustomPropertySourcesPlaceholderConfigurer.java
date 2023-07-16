package demo.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringValueResolver;

public class CustomPropertySourcesPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer {

    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
                                     final ConfigurablePropertyResolver propertyResolver) throws BeansException {

        propertyResolver.setPlaceholderPrefix(this.placeholderPrefix);
        propertyResolver.setPlaceholderSuffix(this.placeholderSuffix);
        propertyResolver.setValueSeparator(this.valueSeparator);

        StringValueResolver valueResolver = new StringValueResolver() {
            @Override
            public String resolveStringValue(String strVal) {
                String resolved = (ignoreUnresolvablePlaceholders ?
                        propertyResolver.resolvePlaceholders(strVal) :
                        propertyResolver.resolveRequiredPlaceholders(strVal));
                if (trimValues) {
                    resolved = resolved.trim();
                }

                if (ObjectUtils.nullSafeEquals(strVal, resolved)) {
                    // 占位符解析后处理
                    resolved = CipherUtil.decrypt(resolved);
                } else {
                    // 占位符解析前处理
                    resolved = CipherUtil.decryptProperty(strVal, resolved);
                }
                return (resolved.equals(nullValue) ? null : resolved);
            }
        };

        doProcessProperties(beanFactoryToProcess, valueResolver);
    }
}

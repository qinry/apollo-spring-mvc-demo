package demo.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@EnableApolloConfig
@Slf4j
public class AppConfig {

    @Bean
    public TestBean testBean() {
        return new TestBean();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public static EncryptPropertySourceProcessor encryptPropertySourceProcessor() {
        return new EncryptPropertySourceProcessor();
    }
}

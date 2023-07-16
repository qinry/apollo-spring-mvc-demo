package demo.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import demo.util.CustomPropertySourcesPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableApolloConfig
public class AppConfig {

    @Bean
    public TestBean testBean() {
        return new TestBean();
    }

    @Bean
    public static CustomPropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        CustomPropertySourcesPlaceholderConfigurer customPropertySourcesPlaceholderConfigurer = new CustomPropertySourcesPlaceholderConfigurer();
        return customPropertySourcesPlaceholderConfigurer;
    }

}
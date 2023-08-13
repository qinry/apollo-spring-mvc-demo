package demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value = {
        "classpath:spring/root-context.xml",
        "classpath:spring/appServlet/servlet-context.xml" })
public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ServletInitializer.class);
    }
}

package demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class TestBean {
    @Value("${timeout:1}")
    private int timeout;

    @Value("${batch:1}")
    private int batch;
}

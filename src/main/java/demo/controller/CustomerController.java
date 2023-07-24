package demo.controller;

import com.alibaba.druid.pool.DruidDataSource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.config.TestBean;
import demo.model.Customer;
import demo.service.CustomerService;
import demo.util.PropertyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/customer")
@ResponseBody
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    private final TestBean testBean;

    private final DruidDataSource dataSource;

    @Value("${jdbc.password}")
    private String password;

    private final Environment environment;

    private final ObjectMapper objectMapper;

    @RequestMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    @RequestMapping("/testBean")
    public TestBean testBean() throws JsonProcessingException {
        log.info("testBean:{}", objectMapper.writeValueAsString(testBean));
        log.info("now:{}", objectMapper.writeValueAsString(new Date()));
        log.info("property:{}:{}", "timeout", PropertyUtil.getProperty("timeout"));
        log.info("property:{}:{}", "timeout", PropertyUtil.resolvePlaceholders("${timeout:1}"));
        log.info("dataSource:password:{}", dataSource.getPassword());
        log.info("property:{}:{}", "jdbc.password", PropertyUtil.getProperty("jdbc.password"));
        log.info("@Value:{}:{}", "jdbc.password", password);
        log.info("Environment:{}:{}", "jdbc.password", environment.getProperty("jdbc.password"));
        return testBean;
    }
}

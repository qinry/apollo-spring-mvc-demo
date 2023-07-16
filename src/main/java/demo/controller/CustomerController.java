package demo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import demo.model.Customer;
import demo.config.TestBean;
import demo.service.CustomerService;
import demo.util.PropertyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
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

    private final DataSource dataSource;

    private static final Gson gson = new GsonBuilder().create();

    @RequestMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    @RequestMapping("/testBean")
    public TestBean testBean() {
        log.info("testBean:{}", gson.toJson(testBean));
        log.info("property:{}:{}", "timeout", PropertyUtil.getProperty("timeout"));
        log.info("dataSource:{}", dataSource);
        log.info("property:{}:{}", "jdbc.password", PropertyUtil.getProperty("jdbc.password"));
        return testBean;
    }
}

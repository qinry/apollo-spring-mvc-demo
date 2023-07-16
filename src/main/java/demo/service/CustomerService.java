package demo.service;

import demo.dao.CustomerMapper;
import demo.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerMapper customerMapper;

    public CustomerService(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public List<Customer> findAll() {
        return customerMapper.selectAll();
    }
}

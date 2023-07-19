package demo.service;

import demo.dao.CustomerMapper;
import demo.model.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerMapper customerMapper;

    public List<Customer> findAll() {
        return customerMapper.selectAll();
    }
}

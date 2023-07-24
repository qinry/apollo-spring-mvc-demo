package demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.dao.CustomerMapper;
import demo.exception.BusinessException;
import demo.exception.BusinessExceptionEnum;
import demo.model.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerMapper customerMapper;

    private final ObjectMapper objectMapper;

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Customer> findAll() {
        List<Customer> customers = customerMapper.selectAll();
        if (CollectionUtils.isEmpty(customers)) {
            throw new BusinessException(BusinessExceptionEnum.CUSTOMER_NOT_FOUND);
        }
        try {
            log.info("findAll:{}", objectMapper.writeValueAsString(customers));
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException!", e);
        }
        return customers;
    }
}

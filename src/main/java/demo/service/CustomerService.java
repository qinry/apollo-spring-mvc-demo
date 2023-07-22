package demo.service;

import com.google.gson.Gson;
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

    private final Gson gson;

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Customer> findAll() {
        List<Customer> customers = customerMapper.selectAll();
        if (CollectionUtils.isEmpty(customers)) {
            throw new BusinessException(BusinessExceptionEnum.CUSTOMER_NOT_FOUND);
        }
        log.info("findAll:{}", gson.toJson(customers));
        return customers;
    }
}

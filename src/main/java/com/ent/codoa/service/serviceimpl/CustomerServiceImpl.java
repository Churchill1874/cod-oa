package com.ent.codoa.service.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.entity.Customer;
import com.ent.codoa.mapper.CustomerMapper;
import com.ent.codoa.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {


}

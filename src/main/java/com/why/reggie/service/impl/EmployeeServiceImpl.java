package com.why.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.reggie.entity.Employee;
import com.why.reggie.mapper.EmployeeMapper;
import com.why.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,
        Employee> implements EmployeeService{

    @Override
    public Employee getEmployeeUsername(String username, String password) {
        return null;
    }

}

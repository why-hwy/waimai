package com.why.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.why.reggie.entity.Employee;

public interface EmployeeService extends IService<Employee> {

    Employee getEmployeeUsername(String username, String password);

}

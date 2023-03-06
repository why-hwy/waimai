package com.why.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.why.reggie.common.R;
import com.why.reggie.entity.Employee;
import com.why.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired(required = false)
    private EmployeeService employeeService;

    @PostMapping("/login")
    @ResponseBody
    public R<Employee> login(HttpServletRequest request,
                             @RequestBody Employee employee) {

//        System.out.println(employee);

        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper = queryWrapper.eq(Employee::getUsername, employee.getUsername());

        Employee emp = employeeService.getOne(queryWrapper);

//        Employee emp;
//        emp=employeeService

        if (emp == null) {
            return R.error("用户名不存在");
        }

        if (!emp.getPassword().equals(password)) {
            return R.error("密码错误");
        }

        if (emp.getStatus() == 0) {
            return R.error("账号不可用");
        }

        request.getSession().setAttribute("employee", emp.getId());

        return R.success(emp);

    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");

        return R.success("退出成功");
    }

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {

        log.info("new employee : {}", employee.toString());

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        Long empid = (Long) request.getSession().getAttribute("employee");

        employee.setCreateUser(empid);
        employee.setUpdateUser(empid);

        log.info("new employee : {}", employee.toString());

//        try {
//            employeeService.save(employee);
//        } catch (Exception ex) {
//            R.error("新增员工失败");
//        }

        employeeService.save(employee);

        return R.success("新增员工成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {

        Page pageInfo = new Page(page, pageSize);

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotEmpty(name),
                Employee::getName, name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request,
                            @RequestBody Employee employee) {

//        log.info(employee.toString());

        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);
        employeeService.updateById(employee);

        return R.success("修改成功");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("查询失败");
    }
}
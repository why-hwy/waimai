package com.why;

import com.why.reggie.service.CategoryService;
import com.why.reggie.service.DishService;
import com.why.reggie.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.UUID;

@SpringBootTest
class BootApplicationTests {

    @Autowired(required = false)
    public EmployeeService employeeService;

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    @Test
    void a(){
        System.out.println(123);
    }
}



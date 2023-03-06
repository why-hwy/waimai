package com.why;

import com.why.reggie.common.R;
import com.why.reggie.service.CategoryService;
import com.why.reggie.service.DishService;
import com.why.reggie.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;

@SpringBootTest
class BootApplicationTests {

    @Autowired(required = false)
    public EmployeeService employeeService;

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    @Test
    void R() {
        String st = "16286621587052953611";
        String str = "1,2,3,4";
        String[] arr = str.split(",");
        for (String i : arr) {
            System.out.println(i);
        }
    }

}

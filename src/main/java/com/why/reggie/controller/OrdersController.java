package com.why.reggie.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.why.reggie.common.R;
import com.why.reggie.entity.Orders;
import com.why.reggie.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping("/userPage")
    public R<Page> page(int page, int pageSize) {
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        ordersService.page(pageInfo);
        return R.success(pageInfo);
    }


}

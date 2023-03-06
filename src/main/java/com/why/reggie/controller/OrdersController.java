package com.why.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.why.reggie.common.BaseContext;
import com.why.reggie.common.R;
import com.why.reggie.entity.AddressBook;
import com.why.reggie.entity.Orders;
import com.why.reggie.entity.ShoppingCart;
import com.why.reggie.service.AddressBookService;
import com.why.reggie.service.OrdersService;
import com.why.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/userPage")
    public R<Page> page(int page, int pageSize) {
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        ordersService.page(pageInfo);
        return R.success(pageInfo);
    }

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders order) {
        ordersService.submit(order);
        return R.success("下单成功");
    }

}

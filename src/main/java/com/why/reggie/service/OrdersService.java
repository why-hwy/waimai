package com.why.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.why.reggie.entity.Orders;

public interface OrdersService extends IService<Orders> {

    void submit(Orders orders);
}

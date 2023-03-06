package com.why.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.reggie.entity.OrderDetail;
import com.why.reggie.mapper.OrderDetailMapper;
import com.why.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
        implements OrderDetailService {
}

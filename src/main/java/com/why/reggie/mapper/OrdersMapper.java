package com.why.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.why.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}
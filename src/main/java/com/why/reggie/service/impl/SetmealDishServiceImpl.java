package com.why.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.reggie.entity.SetmealDish;
import com.why.reggie.mapper.SetmealDishMapper;
import com.why.reggie.service.SetmealDishService;
import org.springframework.stereotype.Service;


@Service
public class SetmealDishServiceImpl extends
        ServiceImpl<SetmealDishMapper, SetmealDish>
        implements SetmealDishService {
}

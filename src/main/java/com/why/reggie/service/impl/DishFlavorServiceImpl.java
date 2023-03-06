package com.why.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.reggie.entity.DishFlavor;
import com.why.reggie.mapper.DIshFlavorMapper;
import com.why.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DIshFlavorMapper, DishFlavor> implements DishFlavorService {
}

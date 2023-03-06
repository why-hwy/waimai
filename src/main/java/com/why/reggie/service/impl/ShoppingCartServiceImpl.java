package com.why.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.reggie.entity.ShoppingCart;
import com.why.reggie.mapper.ShoppingCartMapper;
import com.why.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
        implements ShoppingCartService {


    @Autowired
    private ShoppingCartService shoppingCartService;


    @Transactional
    public void subShoppingCart(ShoppingCart shoppingCart) {
        if (shoppingCart.getSetmealId() != null) {
            Long setmealId = shoppingCart.getSetmealId();
            LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ShoppingCart::getSetmealId, setmealId);
            ShoppingCart shoppingCart1 = shoppingCartService.getOne(queryWrapper);
            int number = shoppingCart1.getNumber() - 1;
            if (number > 0) {
                shoppingCart1.setNumber(number);
                shoppingCartService.updateById(shoppingCart1);
            } else {
                shoppingCartService.removeById(shoppingCart1);
            }
        }else {
            Long dishId = shoppingCart.getDishId();
            LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
            ShoppingCart shoppingCart1 = shoppingCartService.getOne(queryWrapper);
            int number = shoppingCart1.getNumber() - 1;
            if (number > 0) {
                shoppingCart1.setNumber(number);
                shoppingCartService.updateById(shoppingCart1);
            } else {
                shoppingCartService.removeById(shoppingCart1);
            }
        }
    }
}

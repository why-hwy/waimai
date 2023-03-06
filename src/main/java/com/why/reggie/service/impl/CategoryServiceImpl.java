package com.why.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.reggie.common.CustomException;
import com.why.reggie.entity.Category;
import com.why.reggie.entity.Dish;
import com.why.reggie.entity.Setmeal;
import com.why.reggie.mapper.CategoryMapper;
import com.why.reggie.service.CategoryService;
import com.why.reggie.service.DishService;
import com.why.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int countDish = dishService.count(dishLambdaQueryWrapper);

        log.info("关联了{}菜品",countDish);
        if (countDish > 0) {
            throw new CustomException("关联菜品无法删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);

        int countSetmeal = setmealService.count(setmealLambdaQueryWrapper);
        log.info("关联了{}套餐",countSetmeal);
        if (countSetmeal > 0) {
            throw new CustomException("关联套餐无法删除");
        }

        super.removeById(id);
    }
}

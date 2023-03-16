package com.why.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.reggie.common.CustomException;
import com.why.reggie.dto.SetmealDto;
import com.why.reggie.entity.Setmeal;
import com.why.reggie.entity.SetmealDish;
import com.why.reggie.mapper.SetmealMapper;
import com.why.reggie.service.DishService;
import com.why.reggie.service.SetmealDishService;
import com.why.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServicempl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional
    public void saveWithSetmealDsh(SetmealDto setmealDto) {
        this.save(setmealDto);

        Long setmealId = setmealDto.getId();

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealId);
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    @Transactional
    public int[] deleteWithSetmealDish(String ids) {

        int[] ints = new int[2];
        for (String id : ids.split(",")) {
            LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SetmealDish::getSetmealId, Long.parseLong(id));

            Setmeal setmeal = this.getById(id);
            if (setmeal.getStatus() == 1) {
                System.out.println(setmeal.getStatus());
                System.out.println(setmeal.getStatus().getClass());
                ints[1] += 1;
                continue;
            }
            setmealDishService.remove(queryWrapper);
            ints[0] += 1;

            Setmeal byId = this.getById(id);
            String key = "setmealCache::" + byId.getCategoryId() + "_1";
            redisTemplate.delete(key);

            this.removeById(id);
        }

        if (ints[0] == 0) {
            throw new CustomException("所选中套餐全部正在售卖中，无法删除");
        }

        return ints;
    }

    @Override
    public SetmealDto getByIdWithSetmealDish(Long id) {

        Setmeal setmeal = this.getById(id);

        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal, setmealDto);

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, id);

        List<SetmealDish> setmealDishes = setmealDishService.list(queryWrapper);

        setmealDto.setSetmealDishes(setmealDishes);

        return setmealDto;

    }

    @Override
    public void updateWithSetmealDish(SetmealDto setmealDto) {
        this.updateById(setmealDto);

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmealDto.getId());

        setmealDishService.remove(queryWrapper);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        log.info(setmealDishes.toString());

        setmealDishService.saveBatch(setmealDishes);
    }
}

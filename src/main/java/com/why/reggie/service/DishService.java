package com.why.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.why.reggie.dto.DishDto;
import com.why.reggie.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {

    void saveWithFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);

    List<Dish> deleteWithFlavor(String ids);

}

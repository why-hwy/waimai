package com.why.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.why.reggie.dto.DishDto;
import com.why.reggie.entity.Dish;

public interface DishService extends IService<Dish> {

    void saveWithFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);

    void deleteWithFlavor(String ids);

}

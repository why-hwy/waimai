package com.why.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.why.reggie.dto.SetmealDto;
import com.why.reggie.entity.Setmeal;

public interface SetmealService extends IService<Setmeal> {

    void saveWithSetmealDsh(SetmealDto setmealDto);

    int[] deleteWithSetmealDish(String ids);

    SetmealDto getByIdWithSetmealDish(Long id);

    void updateWithSetmealDish(SetmealDto setmealDto);
}

package com.why.reggie.dto;

import com.why.reggie.entity.Setmeal;
import com.why.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {
    private List<SetmealDish> setmealDishes = new ArrayList<>();

    private Long categoryId;

    private String categoryName;
}

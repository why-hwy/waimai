package com.why.reggie.dto;

import com.why.reggie.entity.Dish;
import com.why.reggie.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {
    private List<DishFlavor> flavors=new ArrayList<>();

    private String categoryName;

    private Integer copies;
}

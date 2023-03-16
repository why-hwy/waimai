package com.why.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.why.reggie.common.R;
import com.why.reggie.dto.SetmealDto;
import com.why.reggie.entity.Category;
import com.why.reggie.entity.Setmeal;
import com.why.reggie.service.CategoryService;
import com.why.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> pageDto = new Page<>(page, pageSize);

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Setmeal::getName, name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, pageDto, "records");

        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();

            BeanUtils.copyProperties(item, setmealDto);

            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        pageDto.setRecords(list);

        return R.success(pageDto);
    }

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        setmealService.saveWithSetmealDsh(setmealDto);
        return R.success("添加套餐成功");
    }

    @DeleteMapping
    public R<String> remove(String ids) {
        int[] ints;
        ints = setmealService.deleteWithSetmealDish(ids);
        String string = "删除成功" + ints[0] + "条,无法删除未停售" + ints[1] + "条";
        return R.success(string);
    }

    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable Long id) {
        SetmealDto setmealDto = setmealService.getByIdWithSetmealDish(id);
        return R.success(setmealDto);
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto) {
        setmealService.updateWithSetmealDish(setmealDto);
        return R.success("修改成功");
    }

    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable("status") int status, @NotNull String ids) {

        for (String id : ids.split(",")) {
            Setmeal setmeal = new Setmeal();
            setmeal.setId(Long.parseLong(id));
            if (status == 0) {
                setmeal.setStatus(0);
            } else {
                setmeal.setStatus(1);
            }
            setmealService.updateById(setmeal);
        }
        return R.success("状态修改成功");
    }

    @GetMapping("/list")
    @Cacheable(value = "setmealCache", key = "#setmeal.categoryId+'_'+#setmeal.status")
    public R<List<Setmeal>> getList(@NotNull Setmeal setmeal) {

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null,
                Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(Setmeal::getStatus, 1);

        List<Setmeal> setmeals = setmealService.list(queryWrapper);

        return R.success(setmeals);
    }

}

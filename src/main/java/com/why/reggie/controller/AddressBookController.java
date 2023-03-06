package com.why.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.why.reggie.common.R;
import com.why.reggie.entity.AddressBook;
import com.why.reggie.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/addressBook")
@Slf4j
public class AddressBookController {

    @Autowired
    public AddressBookService addressBookService;

    @GetMapping("/list")
    public R<List<AddressBook>> getList(HttpSession session) {

        Object userId = session.getAttribute("user");

        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, userId);

        List<AddressBook> addressBooks = addressBookService.list(queryWrapper);

        return R.success(addressBooks);
    }

    @PostMapping
    public R<String> save(@RequestBody AddressBook addressBook, HttpSession session) {
        log.info(addressBook.toString());

        Object userId = session.getAttribute("user");

        addressBook.setUserId((Long) userId);

        addressBookService.save(addressBook);

        return R.success("添加成功");
    }

    @GetMapping("{id}")
    public R<AddressBook> get(@PathVariable Long id) {

        log.info("id : {}", id);

        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getId, id);
        AddressBook addressBook = addressBookService.getOne(queryWrapper);
        return R.success(addressBook);
    }

    @PutMapping
    public R<String> update(@RequestBody AddressBook addressBook) {

        addressBookService.updateById(addressBook);

        return R.success("修改成功");

    }


    @PutMapping("/default")
    public R<String> setDefault(@RequestBody AddressBook addressBook, HttpSession session) {

        Object userId = session.getAttribute("user");
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, userId);

        List<AddressBook> addressBooks = addressBookService.list(queryWrapper);

        for (AddressBook x : addressBooks) {
            if (x.getIsDefault() == 1) {
                x.setIsDefault(0);
            }
            if (x.getId().equals(addressBook.getId())) {
                x.setIsDefault(1);
            }
        }

        addressBookService.updateBatchById(addressBooks);

        return R.success("修改成功");
    }

}

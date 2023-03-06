package com.why.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.why.reggie.common.R;
import com.why.reggie.entity.User;
import com.why.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();

        //生成验证码块
        String code = "1111";

        log.info("code : {}", code);

        session.setAttribute(phone, code);

        return R.success("发送成功");
    }

    @PostMapping("/login")
    @ResponseBody
    public R<User> login(HttpSession session,
                         @RequestBody Map map) {

        log.info(map.toString());

        String phone = map.get("phone").toString();
        String code = map.get("code").toString();

        Object codeSeeion = session.getAttribute(phone);


        if (codeSeeion != null && codeSeeion.equals(code)) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);

            User user = userService.getOne(queryWrapper);
            if (user == null) {
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }

            session.setAttribute("user",user.getId());

            log.info("登录成功");

            return R.success(user);
        }

        return R.error("登录失败");
    }

    @PostMapping("/loginout")
    public R<String> loginout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
          
        return R.success("退出成功");
    }

}

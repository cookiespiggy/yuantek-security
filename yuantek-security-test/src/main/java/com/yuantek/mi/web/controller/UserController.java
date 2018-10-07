package com.yuantek.mi.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.yuantek.mi.dto.User;
import com.yuantek.mi.dto.UserQueryCondition;
import com.yuantek.mi.exceptions.UserNotExistException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.yuantek.mi.utils.ObjectUtil.pretty;

@RestController
@RequestMapping("/user")
public class UserController {

//    @GetMapping("/user")
//    public List<User> query(@RequestParam(name = "username", required = false, defaultValue = "jojo") String username) {
//        List<User> users = new ArrayList<>();
//        users.add(new User());
//        users.add(new User());
//        users.add(new User());
//        return users;
//    }

    @GetMapping
    @JsonView(User.UserSimpleView.class)
    public List<User> query(UserQueryCondition condition, @PageableDefault(page = 2, size = 17, sort = "username,asc") Pageable pageable) {

        pretty(condition);
        pretty(pageable);

        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }

    // \d 表示0-9的任意一个数字 写在字符串里面\需要用\来转义
    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getInfo(@PathVariable(name = "id") String id) {
        System.out.println("进入getInfo服务");
        if (!"1".equals(id)) {
            throw new UserNotExistException(id);
        }
        User user = new User();
        user.setUsername("tom");
        return user;
    }

    // @RequestBody 解析前端传过来的json字符串
    @PostMapping
    public User create(@Valid @RequestBody User user, BindingResult errors) {

        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error -> pretty(error));
        }

        pretty(user);

        user.setId("1");
        return user;
    }

    @PutMapping("/{id:\\d+}")
    public User update(@PathVariable String id, @Valid @RequestBody User user, BindingResult errors) {

        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error -> pretty(error));
        }

        pretty(user);

        user.setId("1");
        return user;
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable String id) {
        System.out.println(id);
    }

}

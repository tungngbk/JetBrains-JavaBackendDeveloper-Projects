package com.tungngbk.recipes.controller;

import com.tungngbk.recipes.entity.User;
import com.tungngbk.recipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;

@RestController
public class RegisterController {
    @Autowired
    private UserService userService;

    @PostMapping("/api/register")
    @ResponseStatus(HttpStatus.OK)
    public void register(@Valid @RequestBody User user){
        userService.add(user);
    }
}

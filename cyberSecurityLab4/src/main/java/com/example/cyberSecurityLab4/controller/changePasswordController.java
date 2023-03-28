package com.example.cyberSecurityLab4.controller;

import com.example.cyberSecurityLab4.dao.UserRepository;
import com.example.cyberSecurityLab4.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class changePasswordController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/api/changePassword")
    public @ResponseBody Boolean changePassword(@RequestParam String userName,
                                                @RequestParam String password){
        Optional<User> user = userRepository.findByUserName(userName);
        //不存在此用户
        if(user.isEmpty()){
            return false;
        }
        else{
            user.get().setPassword(password);
            userRepository.save(user.get());
            return true;
        }
    }
}

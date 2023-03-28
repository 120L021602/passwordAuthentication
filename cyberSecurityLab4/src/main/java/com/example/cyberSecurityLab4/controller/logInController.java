package com.example.cyberSecurityLab4.controller;

import com.example.cyberSecurityLab4.cryptography.AESHelper;
import com.example.cyberSecurityLab4.cryptography.SHA256Helper;
import com.example.cyberSecurityLab4.dao.UserRepository;
import com.example.cyberSecurityLab4.domain.User;
import com.example.cyberSecurityLab4.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class logInController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/api/logIn")
    public @ResponseBody Response logIn(@RequestParam String userName,
                                        @RequestParam String hashedValue2,
                                        @RequestParam String authCode) throws Exception {
        Response response = new Response();

        Optional<User> user = userRepository.findByUserName(userName);
        if(user.isEmpty()){
            response.setSuccess(false);
            response.setAESEncryptedAuthCode("Username not defined.");
            return response;
        }

        //与客户端以同样的方法计算得到散列值2'
        String hashedValue2Apostrophy = "";
        String hashedValue1 = SHA256Helper.getSHA256Str(userName + user.get().getPassword());
        hashedValue2Apostrophy = SHA256Helper.getSHA256Str(hashedValue1 + authCode);

        //判断是否相等
        if(hashedValue2Apostrophy.equals(hashedValue2)){
            response.setSuccess(true);
            //如散列值2’=散列值2，则认证成功，成功后用散列值1加密（AES）认证码发送给客户端
            String  AESEncryptedAuthCode = AESHelper.encrypt(authCode, hashedValue1.substring(0, 16), hashedValue1.substring(16, 32));
            response.setAESEncryptedAuthCode(AESEncryptedAuthCode);
        }
        else{
            response.setSuccess(false);
            response.setAESEncryptedAuthCode("Something wrong with the hash value.");
        }

        //返回响应体
        return response;
    }
}

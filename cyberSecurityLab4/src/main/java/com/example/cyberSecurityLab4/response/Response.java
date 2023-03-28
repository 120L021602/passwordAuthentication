package com.example.cyberSecurityLab4.response;

import lombok.Data;

@Data
public class Response {
    boolean success;
    String AESEncryptedAuthCode;

    public Response() {
        this.success = false;
        AESEncryptedAuthCode = "";
    }
}



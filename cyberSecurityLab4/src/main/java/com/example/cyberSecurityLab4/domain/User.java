package com.example.cyberSecurityLab4.domain;

import jakarta.persistence.*;

@Entity//实体类
@Table(name = "user")//对应哪张表
public class User {
    @Id//这是主键
    @Column(name = "id")//数据库中的id对应属性中的id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "userName")
    private String userName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHashedValue1() {
        return hashedValue1;
    }

    public void setHashedValue1(String hashedValue1) {
        this.hashedValue1 = hashedValue1;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "hashedValue1")
    private String hashedValue1;

    @Column(name = "password")
    private String password;


}
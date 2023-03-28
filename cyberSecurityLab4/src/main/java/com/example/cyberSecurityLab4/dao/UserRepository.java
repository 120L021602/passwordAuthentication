package com.example.cyberSecurityLab4.dao;

import com.example.cyberSecurityLab4.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //根据用户名来查询
    List<User> findAllByUserName(String str);

    Optional<User> findByUserName(String userName);
}


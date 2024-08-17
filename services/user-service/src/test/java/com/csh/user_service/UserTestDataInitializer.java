package com.csh.user_service;

import com.csh.user_service.entity.UserEntity;
import com.csh.user_service.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserTestDataInitializer {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    @Transactional
    public void init() {
        userRepository.deleteAll();

        UserEntity userEntity1 = new UserEntity("userId1","passwd","userName","user@mail.com", "ROLE_ADMIN");
        userRepository.save(userEntity1);
    }
}

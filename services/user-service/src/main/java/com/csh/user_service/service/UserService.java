package com.csh.user_service.service;

import com.csh.user_service.entity.UserEntity;
import com.csh.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
//import util.JwtUtil;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    //@Autowired
    //private JwtUtil jwtUtil;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(String id) {
        return userRepository.findById(id);
    }

    public UserEntity createUser(UserEntity userEntity) {

        userRepository.findById(userEntity.getUserId()).ifPresent(findUser -> {
            throw new IllegalArgumentException("User ID already exist");
        });

        userRepository.findByEmail(userEntity.getEmail()).ifPresent(findUser -> {
            throw new IllegalArgumentException("User Email already exist");
        });

        return userRepository.save(userEntity);
    }

/*
    public String login(String userId, String password) {

        if ("admin".equals(userId) && "admin".equals(password)) {
            return jwtUtil.generateToken(new UserEntity("admin","admin","admin","admin","ROLE_ADMIN"));
        }

        UserEntity userEntity = userRepository.findById(userId)
                .filter(findUser -> passwordEncoder.matches(password, findUser.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID or password"));

        return jwtUtil.generateToken(userEntity);
    }
*/
}

package com.tungngbk.recipes.service;

import com.tungngbk.recipes.entity.User;
import com.tungngbk.recipes.exception.UserAlreadyExistsException;
import com.tungngbk.recipes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void add(User user) {
        if (userRepository.existsById(user.getEmail())) {
            throw new UserAlreadyExistsException();
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

}

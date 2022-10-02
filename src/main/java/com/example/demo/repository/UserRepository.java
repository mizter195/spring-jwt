package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    private static final List<User> users = Arrays.asList(
            new User("duonglh", encoder.encode("duonglh"), true, "USER"),
            new User("admin", encoder.encode("admin"), true, "ADMIN")
    );

    public Optional<User> findByUsername(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }
}

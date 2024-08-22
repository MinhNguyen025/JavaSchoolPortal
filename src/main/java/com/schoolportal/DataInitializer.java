package com.schoolportal;

import com.schoolportal.model.RoleName;
import com.schoolportal.model.User;
import com.schoolportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create admin user
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("123"));
        admin.setRole(RoleName.valueOf("ROLE_ADMIN")); // Set role directly
        userRepository.save(admin);

        // Create student user
        User student = new User();
        student.setUsername("student");
        student.setPassword(passwordEncoder.encode("123"));
        student.setRole(RoleName.valueOf("ROLE_STUDENT")); // Set role directly
        userRepository.save(student);

        // Create parent user
        User parent = new User();
        parent.setUsername("parent");
        parent.setPassword(passwordEncoder.encode("123"));
        parent.setRole(RoleName.valueOf("ROLE_PARENT")); // Set role directly
        userRepository.save(parent);

        // Create teacher user
        User teacher = new User();
        teacher.setUsername("teacher");
        teacher.setPassword(passwordEncoder.encode("123"));
        teacher.setRole(RoleName.valueOf("ROLE_TEACHER"));
        userRepository.save(teacher);
        }
    }
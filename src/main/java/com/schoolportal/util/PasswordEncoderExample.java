package com.schoolportal.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderExample {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPasswordAdmin = "adminpassword";
        String rawPasswordStudent = "studentpassword";

        String encodedPasswordAdmin = encoder.encode(rawPasswordAdmin);
        String encodedPasswordStudent = encoder.encode(rawPasswordStudent);

        System.out.println("Admin Encoded Password: " + encodedPasswordAdmin);
        System.out.println("Student Encoded Password: " + encodedPasswordStudent);
    }
}

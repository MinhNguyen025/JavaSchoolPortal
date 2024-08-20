package com.schoolportal.controller;

import com.schoolportal.model.User;
import com.schoolportal.repository.UserRepository;
import com.schoolportal.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService; // Inject the service

    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model) {
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmNewPassword") String confirmNewPassword,
            Authentication authentication,
            Model model) {

        String username = authentication.getName();
        // Sử dụng Optional để lấy User
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Kiểm tra mật khẩu hiện tại
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            model.addAttribute("error", "Current password is incorrect.");
            return "change-password";
        }

        // Kiểm tra mật khẩu mới
        if (!newPassword.equals(confirmNewPassword)) {
            model.addAttribute("error", "New passwords do not match.");
            return "change-password";
        }

        // Cập nhật mật khẩu và lưu
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return "redirect:/profile?passwordChanged";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Return the registration view name
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model) {

        // Check if the username is already taken
        if (customUserDetailsService.isUsernameTaken(username)) {
            model.addAttribute("error", "Username is already taken!");
            return "register"; // Return to the registration view with error
        }

        // Create and save new user
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password)); // Encode the password
        userRepository.save(newUser);

        return "redirect:/login?registrationSuccess"; // Redirect after successful registration
    }
}
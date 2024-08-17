package com.schoolportal.controller;

import com.schoolportal.model.Role;
import com.schoolportal.model.User;
import com.schoolportal.repository.RoleRepository;
import com.schoolportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        // Mã hóa mật khẩu
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Lấy role từ form
        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            Role userRole = roleRepository.findByName(role.getName());
            roles.add(userRole);
        }

        user.setRoles(roles);
        userRepository.save(user);

        return "redirect:/admin/index";
    }

    @GetMapping("/index")
    public String adminIndex(Model model, Authentication authentication) {
        model.addAttribute("role", "ROLE_ADMIN");
        model.addAttribute("content", "admin/index :: adminContent");
        return "layout";
    }
}

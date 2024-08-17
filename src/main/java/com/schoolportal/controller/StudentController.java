package com.schoolportal.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {
    @GetMapping("/index")
    public String studentIndex(Model model, Authentication authentication) {
        model.addAttribute("role", "ROLE_STUDENT");
        model.addAttribute("content", "student/index :: studentContent");
        return "layout";
    }
}

package com.schoolportal.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/admin/index";
        } else if (request.isUserInRole("ROLE_TEACHER")) {
            return "redirect:/teacher/index";
        } else if (request.isUserInRole("ROLE_STUDENT")) {
            return "redirect:/student/index";
        } else if (request.isUserInRole("ROLE_PARENT")) {
            return "redirect:/parent/index";
        }
        return "redirect:/";
    }

    @GetMapping("/")
    public String index() {
        return "index"; // Trả về trang index
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Trả về trang login
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile"; // Trang profile nơi có thể liên kết đến trang đổi mật khẩu
    }
}
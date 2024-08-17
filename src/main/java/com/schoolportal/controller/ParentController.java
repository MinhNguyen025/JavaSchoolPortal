package com.schoolportal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/parent")
public class ParentController {
    @GetMapping("/index")
    public String parentIndex() {
        return "parent/index";
    }
}

package com.schoolportal.controller;

import com.schoolportal.model.Teacher;
import com.schoolportal.model.Timetable;
import com.schoolportal.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TimetableRepository timetableRepository;

    @GetMapping
    public String viewTimetables(Model model, Authentication authentication) {
        Teacher teacher = (Teacher) authentication.getPrincipal();
        List<Timetable> timetables = timetableRepository.findByTeacher(teacher); // Sử dụng phương thức với đối tượng Teacher
        model.addAttribute("timetables", timetables);
        return "teacher/timetables";
    }

    @GetMapping("/index")
    public String teacherIndex() {
        return "teacher/index";
    }
}

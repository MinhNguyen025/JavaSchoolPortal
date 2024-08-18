package com.schoolportal.controller;

import com.schoolportal.model.SchoolClass;
import com.schoolportal.model.Student;
import com.schoolportal.model.Timetable;
import com.schoolportal.repository.SchoolClassRepository;
import com.schoolportal.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    @GetMapping("/index")
    public String studentIndex(Model model, Authentication authentication) {
        model.addAttribute("role", "ROLE_STUDENT");
        model.addAttribute("content", "student/index :: studentContent");
        return "layout";
    }

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @GetMapping("/timetable")
    public String viewTimetable(Model model, Authentication authentication) {
        Student student = (Student) authentication.getPrincipal();
        Long schoolClassId = student.getSchoolClass().getId(); // Lấy ID của lớp học
        SchoolClass schoolClass = schoolClassRepository.findById(schoolClassId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid school class Id:" + schoolClassId));
        List<Timetable> timetables = timetableRepository.findBySchoolClass(schoolClass); // Sử dụng phương thức với đối tượng SchoolClass
        model.addAttribute("timetables", timetables);
        return "student/timetable";
    }

}

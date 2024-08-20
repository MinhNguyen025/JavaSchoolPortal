package com.schoolportal.controller;

import com.schoolportal.model.SchoolClass;
import com.schoolportal.service.SchoolClassService;
import com.schoolportal.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/schoolclasses")
public class SchoolClassController {

    @Autowired
    private SchoolClassService schoolClassService;

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/list")
    public String listSchoolClasses(Model model) {
        List<SchoolClass> schoolClasses = schoolClassService.getAllSchoolClasses();
        model.addAttribute("schoolClasses", schoolClasses);
        return "admin/schoolclasses/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("schoolClass", new SchoolClass());
        // Không cần danh sách giáo viên nữa
        return "admin/schoolclasses/add";
    }

    @PostMapping("/add")
    public String addSchoolClass(@ModelAttribute("schoolClass") SchoolClass schoolClass) {
        schoolClassService.save(schoolClass);
        return "redirect:/admin/schoolclasses/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        SchoolClass schoolClass = schoolClassService.findById(id);
        if (schoolClass != null) {
            model.addAttribute("schoolClass", schoolClass);
            return "admin/schoolclasses/edit";
        } else {
            return "redirect:/admin/schoolclasses/list";
        }
    }

    @PostMapping("/edit/{id}")
    public String editSchoolClass(@PathVariable("id") Long id, @ModelAttribute("schoolClass") SchoolClass schoolClass) {
        schoolClassService.save(schoolClass);
        return "redirect:/admin/schoolclasses/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteSchoolClass(@PathVariable("id") Long id) {
        schoolClassService.deleteById(id);
        return "redirect:/admin/schoolclasses/list";
    }
}

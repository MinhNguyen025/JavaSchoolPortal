package com.schoolportal.controller;

import com.schoolportal.model.Timetable;
import com.schoolportal.repository.SchoolClassRepository;
import com.schoolportal.repository.TeacherRepository;
import com.schoolportal.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@Controller
@RequestMapping("/admin/timetables")
public class TimetableController {

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private SchoolClassRepository classRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping
    public String listTimetables(Model model) {
        model.addAttribute("timetables", timetableRepository.findAll());
        return "admin/timetables";
    }

    @GetMapping("/create")
    public String showCreateTimetableForm(Model model) {
        Timetable timetable = new Timetable();
        model.addAttribute("timetable", timetable);
        model.addAttribute("classes", classRepository.findAll());
        model.addAttribute("teachers", teacherRepository.findAll());
        return "admin/create-timetable";
    }


    @PostMapping("/create")
    public String createTimetable(@ModelAttribute("timetable") Timetable timetable) {
        timetableRepository.save(timetable);
        return "redirect:/admin/timetables";
    }

    @GetMapping("/edit/{id}")
    public String showEditTimetableForm(@PathVariable("id") Long id, Model model) {
        Timetable timetable = timetableRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid timetable Id:" + id));
        model.addAttribute("timetable", timetable);
        model.addAttribute("classes", classRepository.findAll());
        model.addAttribute("teachers", teacherRepository.findAll());
        return "admin/edit-timetable";
    }

    @PostMapping("/edit/{id}")
    public String updateTimetable(@PathVariable("id") Long id, @ModelAttribute("timetable") Timetable timetable) {
        timetable.setId(id);
        timetableRepository.save(timetable);
        return "redirect:/admin/timetables";
    }

    @GetMapping("/delete/{id}")
    public String deleteTimetable(@PathVariable Long id) {
        timetableRepository.deleteById(id);
        return "redirect:/admin/timetables";
    }
}

package com.schoolportal.controller;

import com.schoolportal.model.Timetable;
import com.schoolportal.repository.SubjectRepository;
import com.schoolportal.repository.TeacherRepository;
import com.schoolportal.repository.TimeSlotRepository;
import com.schoolportal.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/timetable")
public class TimetableController {

    @Autowired
    private TimetableService timetableService;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    public String showAddTimetableForm(Model model) {
        model.addAttribute("timetable", new Timetable());
        model.addAttribute("subjects", subjectRepository.findAll());
        model.addAttribute("teachers", teacherRepository.findAll());
        model.addAttribute("timeSlots", timeSlotRepository.findAll());
        return "admin/add-timetable";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String addTimetable(@ModelAttribute Timetable timetable) {
        timetableService.addTimetable(timetable);
        return "redirect:/admin/timetable/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public String listTimetables(Model model) {
        model.addAttribute("timetables", timetableService.getAllTimetables());
        return "admin/timetable-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteTimetable(@PathVariable Long id) {
        timetableService.deleteTimetable(id);
        return "redirect:/admin/timetable/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditTimetableForm(@PathVariable Long id, Model model) {
        Timetable timetable = timetableService.getTimetableById(id);
        model.addAttribute("timetable", timetable);
        model.addAttribute("subjects", subjectRepository.findAll());
        model.addAttribute("teachers", teacherRepository.findAll());
        model.addAttribute("timeSlots", timeSlotRepository.findAll());
        return "admin/edit-timetable";
    }

    @PostMapping("/update")
    public String updateTimetable(@ModelAttribute Timetable timetable) {
        Timetable existingTimetable = timetableService.getTimetableById(timetable.getId());
        if (existingTimetable != null) {
            existingTimetable.setDay(timetable.getDay());
            existingTimetable.setTimeSlot(timetable.getTimeSlot());
            existingTimetable.setSubject(timetable.getSubject());
            existingTimetable.setTeacher(timetable.getTeacher());
            existingTimetable.setSchoolClass(timetable.getSchoolClass());
            timetableService.addTimetable(existingTimetable);
        }
        return "redirect:/admin/timetable/list";
    }

}

package com.schoolportal.controller;

import com.schoolportal.model.Subject;
import com.schoolportal.repository.SubjectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/subjects")
public class SubjectController {

    private final SubjectRepository subjectRepository;

    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @GetMapping
    public String listSubjects(Model model) {
        model.addAttribute("subjects", subjectRepository.findAll());
        return "admin/subjects";
    }

    @GetMapping("/create")
    public String createSubjectForm(Model model) {
        model.addAttribute("subject", new Subject());
        return "admin/create-subject";
    }

    @PostMapping
    public String saveSubject(@ModelAttribute Subject subject) {
        subjectRepository.save(subject);
        return "redirect:/admin/subjects";
    }

    @GetMapping("/edit/{id}")
    public String editSubjectForm(@PathVariable Long id, Model model) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new RuntimeException("Subject not found"));
        model.addAttribute("subject", subject);
        return "admin/edit-subject";
    }

    @PostMapping("/update")
    public String updateSubject(@ModelAttribute Subject subject) {
        subjectRepository.save(subject);
        return "redirect:/admin/subjects";
    }

    @GetMapping("/delete/{id}")
    public String deleteSubject(@PathVariable Long id) {
        subjectRepository.deleteById(id);
        return "redirect:/admin/subjects";
    }
}

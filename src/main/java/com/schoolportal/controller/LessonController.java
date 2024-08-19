package com.schoolportal.controller;

import com.schoolportal.model.Lesson;
import com.schoolportal.model.Subject;
import com.schoolportal.repository.LessonRepository;
import com.schoolportal.repository.SubjectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/lessons")
public class LessonController {

    private final LessonRepository lessonRepository;
    private final SubjectRepository subjectRepository;

    public LessonController(LessonRepository lessonRepository, SubjectRepository subjectRepository) {
        this.lessonRepository = lessonRepository;
        this.subjectRepository = subjectRepository;
    }

    @GetMapping
    public String listLessons(Model model) {
        model.addAttribute("lessons", lessonRepository.findAll());
        return "admin/lessons";
    }

    @GetMapping("/create")
    public String createLessonForm(Model model) {
        model.addAttribute("lesson", new Lesson());
        model.addAttribute("subjects", subjectRepository.findAll()); // Get all subjects
        return "admin/create-lesson";
    }

    @PostMapping
    public String saveLesson(@ModelAttribute Lesson lesson) {
        lessonRepository.save(lesson);
        return "redirect:/admin/lessons";
    }

    @GetMapping("/edit/{id}")
    public String editLessonForm(@PathVariable Long id, Model model) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new RuntimeException("Lesson not found"));
        model.addAttribute("lesson", lesson);
        model.addAttribute("subjects", subjectRepository.findAll()); // Get all subjects
        return "admin/edit-lesson";
    }

    @PostMapping("/update")
    public String updateLesson(@ModelAttribute Lesson lesson) {
        lessonRepository.save(lesson);
        return "redirect:/admin/lessons";
    }

    @GetMapping("/delete/{id}")
    public String deleteLesson(@PathVariable Long id) {
        lessonRepository.deleteById(id);
        return "redirect:/admin/lessons";
    }
}

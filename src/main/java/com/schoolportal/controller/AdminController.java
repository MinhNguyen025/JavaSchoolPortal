package com.schoolportal.controller;

import com.schoolportal.model.Parent;
import com.schoolportal.model.RoleName; // Import the RoleName enum
import com.schoolportal.model.Student;
import com.schoolportal.model.Teacher;
import com.schoolportal.model.User;
import com.schoolportal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        // No longer need roles from repository
        return "admin/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set the role directly
        user.setRole(RoleName.valueOf(user.getRole().name())); // Assuming the role is set in the form

        userRepository.save(user);
        return "redirect:/admin/index";
    }

    @GetMapping("/index")
    public String adminIndex(Model model, Authentication authentication) {
        model.addAttribute("role", "ROLE_ADMIN");
        model.addAttribute("content", "admin/index :: adminContent");
        return "admin/index";
    }

    @GetMapping("/teachers")
    public String listTeachers(Model model) {
        model.addAttribute("teachers", teacherRepository.findAll());
        return "admin/teachers";
    }

    @GetMapping("/teachers/create")
    public String createTeacherForm(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "admin/create-teacher";
    }

    @PostMapping("/teachers/create")
    public String createTeacher(@ModelAttribute("teacher") Teacher teacher,
                                @RequestParam("username") String username,
                                @RequestParam("password") String password) {
        // Save teacher to the database
        teacherRepository.save(teacher);

        // Create user account with TEACHER role
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        // Set role directly
        user.setRole(RoleName.ROLE_TEACHER);
        userRepository.save(user);

        return "redirect:/admin/teachers";
    }

    @GetMapping("/teachers/edit/{id}")
    public String showEditTeacherForm(@PathVariable("id") Long id, Model model) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid teacher Id:" + id));
        model.addAttribute("teacher", teacher);
        return "admin/edit-teacher";
    }

    @PostMapping("/teachers/edit/{id}")
    public String updateTeacher(@PathVariable("id") Long id, @ModelAttribute("teacher") Teacher teacher) {
        teacher.setId(id);
        teacherRepository.save(teacher);
        return "redirect:/admin/teachers";
    }

    @GetMapping("/teachers/delete/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        teacherRepository.deleteById(id);
        return "redirect:/admin/teachers";
    }

    // Student Management
    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        return "admin/students";
    }

    @GetMapping("/students/create")
    public String showCreateStudentForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("parents", parentRepository.findAll());
        return "admin/create-student";
    }

    @PostMapping("/students/create")
    public String createStudent(
            @ModelAttribute("student") Student student,
            @RequestParam("username") String username,
            @RequestParam("password") String password) {

        // Save student to the database
        studentRepository.save(student);

        // Create user account with STUDENT role
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        // Set role directly
        user.setRole(RoleName.ROLE_STUDENT);
        userRepository.save(user);

        return "redirect:/admin/students";
    }

    @GetMapping("/students/edit/{id}")
    public String showEditStudentForm(@PathVariable("id") Long id, Model model) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        model.addAttribute("student", student);
        model.addAttribute("parents", parentRepository.findAll());
        return "admin/edit-student";
    }

    @PostMapping("/students/edit/{id}")
    public String updateStudent(@PathVariable("id") Long id, @ModelAttribute("student") Student student) {
        student.setId(id);
        studentRepository.save(student);
        return "redirect:/admin/students";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
        return "redirect:/admin/students";
    }

    // Parent Management
    @GetMapping("/parents")
    public String listParents(Model model) {
        model.addAttribute("parents", parentRepository.findAll());
        return "admin/parents";
    }

    @GetMapping("/parents/create")
    public String showCreateParentForm(Model model) {
        model.addAttribute("parent", new Parent());
        return "admin/create-parent";
    }

    @PostMapping("/parents/create")
    public String createParent(
            @ModelAttribute("parent") Parent parent,
            @RequestParam("username") String username,
            @RequestParam("password") String password) {

        // Save parent to the database
        parentRepository.save(parent);

        // Create user account with PARENT role
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        // Set role directly
        user.setRole(RoleName.ROLE_PARENT);
        userRepository.save(user);

        return "redirect:/admin/parents";
    }

    @GetMapping("/parents/edit/{id}")
    public String showEditParentForm(@PathVariable("id") Long id, Model model) {
        Parent parent = parentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid parent Id:" + id));
        model.addAttribute("parent", parent);
        return "admin/edit-parent";
    }

    @PostMapping("/parents/edit/{id}")
    public String updateParent(@PathVariable("id") Long id, @ModelAttribute("parent") Parent parent) {
        Parent existingParent = parentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid parent Id:" + id));

        // Update basic information
        existingParent.setFirstName(parent.getFirstName());
        existingParent.setLastName(parent.getLastName());
        existingParent.setEmail(parent.getEmail());
        existingParent.setDateOfBirth(parent.getDateOfBirth());

        // Handle list of students
        if (parent.getStudents() == null) {
            parent.setStudents(new HashSet<>());
        }

        // Clear existing students
        existingParent.getStudents().clear();

        // Add new students from form
        existingParent.getStudents().addAll(parent.getStudents());

        // Save changes
        parentRepository.save(existingParent);
        return "redirect:/admin/parents";
    }

    @GetMapping("/parents/delete/{id}")
    public String deleteParent(@PathVariable Long id) {
        parentRepository.deleteById(id);
        return "redirect:/admin/parents";
    }

    @GetMapping("/parents/{id}/students")
    public String listStudentsByParent(@PathVariable("id") Long parentId, Model model) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid parent Id:" + parentId));
        List<Student> students = studentRepository.findByParent(parent);

        // Format date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        students = students.stream()
                .peek(student -> student.setFormattedDateOfBirth(student.getDateOfBirth().format(formatter)))
                .collect(Collectors.toList());

        model.addAttribute("parent", parent);
        model.addAttribute("students", students);
        return "admin/students-by-parent";
    }
}
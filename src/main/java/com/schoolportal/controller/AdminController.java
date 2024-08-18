package com.schoolportal.controller;

import com.schoolportal.model.Parent;
import com.schoolportal.model.Role;
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
    private RoleRepository roleRepository;

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
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        // Mã hóa mật khẩu
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Lấy role từ form
        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            Role userRole = roleRepository.findByName(role.getName());
            roles.add(userRole);
        }

        user.setRoles(roles);
        userRepository.save(user);

        return "redirect:/admin/index";
    }

    @GetMapping("/index")
    public String adminIndex(Model model, Authentication authentication) {
        model.addAttribute("role", "ROLE_ADMIN");
        model.addAttribute("content", "admin/index :: adminContent");
        return "layout";
    }

    @GetMapping("/teachers")
    public String listTeachers(Model model) {
        model.addAttribute("teachers", teacherRepository.findAll());
        return "admin/teachers";
    }

    @GetMapping("/teachers/create")
    public String showCreateTeacherForm(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "admin/create-teacher"; // Đây là file create-teacher.html
    }

    @PostMapping("/teachers/create")
    public String createTeacher(@ModelAttribute("teacher") Teacher teacher) {
        teacherRepository.save(teacher);
        return "redirect:/admin/teachers";
    }

    @GetMapping("/teachers/edit/{id}")
    public String showEditTeacherForm(@PathVariable("id") Long id, Model model) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid teacher Id:" + id));
        model.addAttribute("teacher", teacher);
        return "admin/edit-teacher"; // Đây là file edit-teacher.html
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

    // Thêm chức năng quản lý Student
    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        return "admin/students"; // Đây là file students.html
    }

    @GetMapping("/students/create")
    public String showCreateStudentForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("parents", parentRepository.findAll()); // Cung cấp danh sách phụ huynh cho form
        return "admin/create-student"; // Đây là file create-student.html
    }

    @PostMapping("/students/create")
    public String createStudent(@ModelAttribute("student") Student student) {
        studentRepository.save(student);
        return "redirect:/admin/students";
    }

    @GetMapping("/students/edit/{id}")
    public String showEditStudentForm(@PathVariable("id") Long id, Model model) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        model.addAttribute("student", student);
        model.addAttribute("parents", parentRepository.findAll()); // Cung cấp danh sách phụ huynh cho form
        return "admin/edit-student"; // Đây là file edit-student.html
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

    // Thêm chức năng quản lý Parent
    @GetMapping("/parents")
    public String listParents(Model model) {
        model.addAttribute("parents", parentRepository.findAll());
        return "admin/parents"; // Đây là file parents.html
    }

    @GetMapping("/parents/create")
    public String showCreateParentForm(Model model) {
        model.addAttribute("parent", new Parent());
        return "admin/create-parent"; // Đây là file create-parent.html
    }

    @PostMapping("/parents/create")
    public String createParent(@ModelAttribute("parent") Parent parent) {
        parentRepository.save(parent);
        return "redirect:/admin/parents";
    }

    @GetMapping("/parents/edit/{id}")
    public String showEditParentForm(@PathVariable("id") Long id, Model model) {
        Parent parent = parentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid parent Id:" + id));
        model.addAttribute("parent", parent);
        return "admin/edit-parent"; // Đây là file edit-parent.html
    }

    @PostMapping("/parents/edit/{id}")
    public String updateParent(@PathVariable("id") Long id, @ModelAttribute("parent") Parent parent) {
        Parent existingParent = parentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid parent Id:" + id));

        // Cập nhật thông tin cơ bản
        existingParent.setFirstName(parent.getFirstName());
        existingParent.setLastName(parent.getLastName());
        existingParent.setEmail(parent.getEmail());
        existingParent.setDateOfBirth(parent.getDateOfBirth());

        // Xử lý danh sách students
        if (parent.getStudents() == null) {
            parent.setStudents(new HashSet<>());
        }

        // Xóa tất cả học sinh hiện tại
        existingParent.getStudents().clear();

        // Thêm học sinh mới từ form
        existingParent.getStudents().addAll(parent.getStudents());

        // Lưu thay đổi
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

        // Định dạng ngày tháng
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        students = students.stream()
                .peek(student -> student.setFormattedDateOfBirth(student.getDateOfBirth().format(formatter)))
                .collect(Collectors.toList());

        model.addAttribute("parent", parent);
        model.addAttribute("students", students);
        return "admin/students-by-parent";
    }



}

package com.schoolportal.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Timetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // Tiêu đề thời khóa biểu (ví dụ: "Thời khóa biểu tuần 1")

    @OneToMany(mappedBy = "timetable", cascade = CascadeType.ALL)
    private List<Timeslot> timeslots; // Danh sách các khung giờ trong thời khóa biểu

    @ManyToOne
    @JoinColumn(name = "school_class_id")
    private SchoolClass schoolClass; // Lớp học của thời khóa biểu

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    // Getters và Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Timeslot> getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(List<Timeslot> timeslots) {
        this.timeslots = timeslots;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}

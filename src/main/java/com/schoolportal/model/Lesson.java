package com.schoolportal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Tên của tiết học (ví dụ: "Tiết 1", "Tiết 2")

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject; // Môn học của tiết học

    @ManyToOne
    @JoinColumn(name = "timetable_id")
    private Timetable timetable; // Thời khóa biểu mà tiết học thuộc về

    @ManyToOne
    @JoinColumn(name = "school_class_id")
    private SchoolClass schoolClass; // Lớp học mà tiết học thuộc về

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher; // Giáo viên dạy tiết học

    private String startTime; // Thời gian bắt đầu tiết học (ví dụ: "08:00")
    private String endTime; // Thời gian kết thúc tiết học (ví dụ: "09:00")

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

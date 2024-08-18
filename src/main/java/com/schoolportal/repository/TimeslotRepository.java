package com.schoolportal.repository;

import com.schoolportal.model.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {
    // Truy vấn theo timetables mà có liên kết với SchoolClass
    List<Timeslot> findByTimetable_SchoolClass_Id(Long schoolClassId);

    List<Timeslot> findByTeacher_Id(Long teacherId);
}

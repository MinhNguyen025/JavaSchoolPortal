package com.schoolportal.repository;

import com.schoolportal.model.SchoolClass;
import com.schoolportal.model.Timetable;
import com.schoolportal.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {
    List<Timetable> findBySchoolClass(SchoolClass schoolClass);
    List<Timetable> findByTeacher(Teacher teacher);
}

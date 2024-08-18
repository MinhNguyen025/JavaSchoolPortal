package com.schoolportal.repository;

import com.schoolportal.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    // Add custom query methods if necessary
}

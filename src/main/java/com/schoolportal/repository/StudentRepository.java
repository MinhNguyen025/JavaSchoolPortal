package com.schoolportal.repository;

import com.schoolportal.model.Parent;
import com.schoolportal.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByParent(Parent parent);
}

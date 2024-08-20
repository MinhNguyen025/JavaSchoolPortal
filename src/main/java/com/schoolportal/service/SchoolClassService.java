package com.schoolportal.service;

import com.schoolportal.model.SchoolClass;
import com.schoolportal.repository.SchoolClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolClassService {

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    public List<SchoolClass> findAll() {
        return schoolClassRepository.findAll();
    }

    public SchoolClass findById(Long id) {
        return schoolClassRepository.findById(id).orElse(null);
    }

    public void save(SchoolClass schoolClass) {
        schoolClassRepository.save(schoolClass);
    }

    public void deleteById(Long id) {
        schoolClassRepository.deleteById(id);
    }

    public List<SchoolClass> getAllSchoolClasses() {
        return schoolClassRepository.findAll();
    }
}

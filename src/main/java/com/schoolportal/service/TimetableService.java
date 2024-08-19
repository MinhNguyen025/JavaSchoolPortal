package com.schoolportal.service;

import com.schoolportal.model.SchoolClass;
import com.schoolportal.model.Timetable;
import com.schoolportal.repository.SchoolClassRepository;
import com.schoolportal.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimetableService {

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    public Timetable saveTimetable(Timetable timetable) {
        return timetableRepository.save(timetable);
    }

    public List<Timetable> getTimetablesByClass(Long classId) {
        SchoolClass schoolClass = schoolClassRepository.findById(classId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid class Id:" + classId));
        return timetableRepository.findBySchoolClass(schoolClass);
    }
}

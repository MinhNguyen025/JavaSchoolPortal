package com.schoolportal.service;

import com.schoolportal.model.SchoolClass;
import com.schoolportal.model.Timeslot;
import com.schoolportal.model.Timetable;
import com.schoolportal.repository.SchoolClassRepository;
import com.schoolportal.repository.TimeslotRepository;
import com.schoolportal.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimetableService {

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private TimeslotRepository timeslotRepository;

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

    public List<Timeslot> getTimeslotsByClass(Long classId) {
        return timeslotRepository.findByTimetable_SchoolClass_Id(classId);
    }

    public List<Timeslot> getTimeslotsByTeacher(Long teacherId) {
        return timeslotRepository.findByTeacher_Id(teacherId);
    }
}

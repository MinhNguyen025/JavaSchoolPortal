package com.schoolportal.service;

import com.schoolportal.model.Timetable;
import com.schoolportal.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimetableService {

    @Autowired
    private TimetableRepository timetableRepository;

    public void addTimetable(Timetable timetable) {
        timetableRepository.save(timetable);
    }

    public List<Timetable> getAllTimetables() {
        return timetableRepository.findAll();
    }

    public void deleteTimetable(Long id) {
        timetableRepository.deleteById(id);
    }

    public Timetable getTimetableById(Long id) {
        return timetableRepository.findById(id).orElse(null);
    }
}


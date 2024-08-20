package com.schoolportal.model;
import jakarta.persistence.*;

@Entity
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String timeSlot;

    // Constructors
    public TimeSlot() {}

    public TimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }
}

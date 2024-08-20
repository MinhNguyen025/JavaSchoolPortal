package com.schoolportal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String title;

    @Column(length = 3000)
    private String content;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    @Transient
    private String formattedCreatedAt;

    // Constructors, Getters, Setters
    public News() {
    }

    public News(String title, String content) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters

    public String getFormattedCreatedAt() {
        return formattedCreatedAt;
    }

    public void setFormattedCreatedAt(String formattedCreatedAt) {
        this.formattedCreatedAt = formattedCreatedAt;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

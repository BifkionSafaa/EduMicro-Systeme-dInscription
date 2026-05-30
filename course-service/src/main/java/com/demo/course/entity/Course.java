package com.demo.course.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String title;
    
    private String description;
    
    private Integer credits;
    
    // Constructeur vide (obligatoire pour JPA)
    public Course() {
    }
    
    // Constructeur avec tous les champs (sauf id)
    public Course(String title, String description, Integer credits) {
        this.title = title;
        this.description = description;
        this.credits = credits;
    }
    
    // Constructeur avec id (pour les tests)
    public Course(Long id, String title, String description, Integer credits) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.credits = credits;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Integer getCredits() {
        return credits;
    }
    
    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setCredits(Integer credits) {
        this.credits = credits;
    }
    
    // toString (optionnel, utile pour le debug)
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", credits=" + credits +
                '}';
    }
}
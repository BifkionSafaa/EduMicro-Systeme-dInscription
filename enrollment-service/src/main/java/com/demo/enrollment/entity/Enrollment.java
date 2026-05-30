package com.demo.enrollment.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
public class Enrollment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long studentId;
    
    @Column(nullable = false)
    private Long courseId;
    
    @Column(nullable = false)
    private LocalDateTime enrollmentDate;
    
    // Constructeur vide (obligatoire pour JPA)
    public Enrollment() {
    }
    
    // Constructeur avec tous les champs
    public Enrollment(Long studentId, Long courseId, LocalDateTime enrollmentDate) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public Long getStudentId() {
        return studentId;
    }
    
    public Long getCourseId() {
        return courseId;
    }
    
    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }
    
    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    
    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
    
    public void setEnrollmentDate(LocalDateTime enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
    
    // toString 
    @Override
    public String toString() {
        return "Enrollment{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", enrollmentDate=" + enrollmentDate +
                '}';
    }
}
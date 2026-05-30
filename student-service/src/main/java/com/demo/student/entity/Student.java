package com.demo.student.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String cnie;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    // Obligatoire pour JPA/Hibernate (SELECT) et Spring (JSON POST) : new Student() puis setters
    public Student() {
    }
    
    // Optionnel : non utilisé dans le flux actuel (création via JSON @RequestBody + constructeur vide).
    // Utile pour tests ou création manuelle en Java : new Student(cnie, firstName, lastName, email)
    public Student(String cnie, String firstName, String lastName, String email) {
        this.cnie = cnie;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    // Optionnel : non utilisé en production ; réservé aux tests (objet Student déjà avec un id).
    public Student(Long id, String cnie, String firstName, String lastName, String email) {
        this.id = id;
        this.cnie = cnie;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public String getCnie() {
        return cnie;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setCnie(String cnie) {
        this.cnie = cnie;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    // toString (optionnel, utile pour le debug)
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", cnie='" + cnie + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

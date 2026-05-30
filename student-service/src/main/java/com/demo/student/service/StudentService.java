package com.demo.student.service;

import com.demo.student.entity.Student;
import com.demo.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    // Récupérer tous les étudiants
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    // Récupérer un étudiant par son ID
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
    
    // Récupérer un étudiant par son CNIE (utilisé par Enrollment Service)
    public Optional<Student> getStudentByCnie(String cnie) {
        return studentRepository.findByCnie(cnie);
    }
    
    // Créer un nouvel étudiant
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }
    
    // Supprimer un étudiant
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
    
    // Vérifier si un étudiant existe
    public boolean existsByCnie(String cnie) {
        return studentRepository.existsByCnie(cnie);
    }
}

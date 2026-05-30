package com.demo.student.repository;

import com.demo.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    // Trouver un étudiant par son CNIE
    Optional<Student> findByCnie(String cnie);
    
    // Vérifier si un CNIE existe déjà
    boolean existsByCnie(String cnie);
}

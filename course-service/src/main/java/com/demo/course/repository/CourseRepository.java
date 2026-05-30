package com.demo.course.repository;

import com.demo.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	// Pas besoin d'ajouter de méthodes !
	// JpaRepository fournit déjà : findAll(), findById(), save(), delete(), etc.
}

package com.arsene.eportfolio.model.data;

import com.arsene.eportfolio.model.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
}
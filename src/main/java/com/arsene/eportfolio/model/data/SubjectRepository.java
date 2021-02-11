package com.arsene.eportfolio.model.data;

import com.arsene.eportfolio.model.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    List<Subject> findByOrderByIdAsc();

}
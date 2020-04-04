package com.arsene.eportfolio.model.data;

import com.arsene.eportfolio.model.entities.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, String> {
}

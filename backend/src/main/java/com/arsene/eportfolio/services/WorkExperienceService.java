package com.arsene.eportfolio.services;

import com.arsene.eportfolio.model.entities.WorkExperience;

import java.util.List;

public interface WorkExperienceService {
    List<WorkExperience> getAll();

    WorkExperience get(String id);

    WorkExperience create(WorkExperience workExperience);

    WorkExperience update(String id1, WorkExperience workExperience);

    void delete(String id);
}

package com.arsene.eportfolio.services;

import com.arsene.eportfolio.exceptions.ResourceNotFoundException;
import com.arsene.eportfolio.model.data.WorkExperienceRepository;
import com.arsene.eportfolio.model.entities.WorkExperience;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WorkExperienceServiceImpl implements WorkExperienceService {

    private WorkExperienceRepository workExperienceRepository;

    @Override
    public List<WorkExperience> getAll() {
        return workExperienceRepository.findAll();
    }

    @Override
    public WorkExperience get(String id) {
        return workExperienceRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public WorkExperience create(WorkExperience workExperience) {
        return workExperienceRepository.save(workExperience);
    }

    @Override
    public WorkExperience update(String id, WorkExperience workExperience) {
        WorkExperience workExperienceToUpdate = this.workExperienceRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        if (workExperience.getPosition() != null) {
            workExperienceToUpdate.setPosition(workExperience.getPosition());
        }
        if (workExperience.getDescription() != null) {
            workExperienceToUpdate.setDescription(workExperience.getDescription());
        }
        if (workExperience.getCompany() != null) {
            workExperienceToUpdate.setCompany(workExperience.getCompany());
        }
        if (workExperience.getStartYear() != null) {
            workExperienceToUpdate.setStartYear(workExperience.getStartYear());
        }
        if (workExperience.getEndYear() != null) {
            workExperienceToUpdate.setEndYear(workExperience.getEndYear());
        }

        return this.workExperienceRepository.save(workExperienceToUpdate);
    }

    @Override
    public void delete(String id) {
        this.workExperienceRepository.deleteById(id);
    }
}

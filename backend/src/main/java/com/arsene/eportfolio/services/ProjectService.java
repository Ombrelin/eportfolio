package com.arsene.eportfolio.services;

import com.arsene.eportfolio.model.dtos.CreateProjectDto;
import com.arsene.eportfolio.model.dtos.ProjectDto;
import com.arsene.eportfolio.model.dtos.UpdateProjectDto;

import java.util.List;

public interface ProjectService {
    ProjectDto createProject(CreateProjectDto dto);

    List<ProjectDto> getProjects();

    void deleteProject(Integer projectId);

    ProjectDto updateProject(UpdateProjectDto dto);

    ProjectDto addAbilityToProject(Integer projectId, Integer abilityId);

    void removeAbilityFromProject(Integer projectId, Integer abilityId);

    ProjectDto addTechnologyToProject(Integer projectId, Integer technologyId);

    void removeTechnologyFromProject(Integer projectId, Integer technologyId);
}

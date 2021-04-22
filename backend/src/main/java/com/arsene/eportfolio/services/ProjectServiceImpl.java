package com.arsene.eportfolio.services;

import com.arsene.eportfolio.exceptions.ResourceNotFoundException;
import com.arsene.eportfolio.model.data.AbilityRepository;
import com.arsene.eportfolio.model.data.ProjectRepository;
import com.arsene.eportfolio.model.data.TechnologyRepository;
import com.arsene.eportfolio.model.dtos.CreateProjectDto;
import com.arsene.eportfolio.model.dtos.ProjectDto;
import com.arsene.eportfolio.model.dtos.UpdateProjectDto;
import com.arsene.eportfolio.model.entities.Project;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final AbilityRepository abilityRepository;
    private final TechnologyRepository technologyRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, AbilityRepository abilityRepository, TechnologyRepository technologyRepository) {
        this.projectRepository = projectRepository;
        this.abilityRepository = abilityRepository;
        this.technologyRepository = technologyRepository;
    }

    @Override
    public ProjectDto createProject(CreateProjectDto dto) {
        var abilities = Set.copyOf(abilityRepository.findByIdIn(dto.getAbilities()));
        var technologies = Set.copyOf(technologyRepository.findByIdIn(dto.getTechnologies()));

        var project = new Project(dto.getName(), dto.getIcon(), dto.getDescription(), dto.getGit(), dto.getColor(), abilities, technologies);
        projectRepository.save(project);

        return new ProjectDto(project);
    }

    @Override
    public List<ProjectDto> getProjects() {
        return projectRepository
                .findAll()
                .stream()
                .map(ProjectDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProject(Integer projectId) {
        this.projectRepository.deleteById(projectId);
    }

    @Override
    public ProjectDto updateProject(UpdateProjectDto dto) {
        var project = this.projectRepository
                .findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException(String.format("No project with id %d", dto.getId())));

        project.setName(dto.getName());
        project.setColor(dto.getColor());
        project.setDescription(dto.getDescription());
        project.setGit(dto.getGit());
        project.setIcon(dto.getIcon());
        projectRepository.save(project);

        return new ProjectDto(project);
    }

    @Override
    public ProjectDto addAbilityToProject(Integer projectId, Integer abilityId) {
        var project = projectRepository.findById(projectId).orElseThrow(ResourceNotFoundException::new);
        var ability = abilityRepository.findById(abilityId).orElseThrow(ResourceNotFoundException::new);
        project.getAbilities().add(ability);
        projectRepository.save(project);

        return new ProjectDto(project);
    }


    @Override
    public ProjectDto addTechnologyToProject(Integer projectId, Integer technologyId) {
        var project = projectRepository.findById(projectId).orElseThrow(ResourceNotFoundException::new);
        var tech = technologyRepository.findById(technologyId).orElseThrow(ResourceNotFoundException::new);
        project.getTechnologies().add(tech);
        projectRepository.save(project);
        return new ProjectDto(project);
    }

    @Override
    public void removeAbilityFromProject(Integer projectId, Integer abilityId) {
        var project = projectRepository.findById(projectId).orElseThrow(ResourceNotFoundException::new);
        project.setAbilities(
                project.getAbilities()
                        .stream()
                        .filter(ability -> !ability.getId().equals(abilityId))
                        .collect(Collectors.toSet())
        );

        projectRepository.save(project);
    }

    @Override
    public void removeTechnologyFromProject(Integer projectId, Integer technologyId) {
        var project = projectRepository.findById(projectId).orElseThrow(ResourceNotFoundException::new);
        project.setTechnologies(
                project.getTechnologies()
                        .stream()
                        .filter(tech -> !tech.getId().equals(technologyId))
                        .collect(Collectors.toSet())
        );

        projectRepository.save(project);
    }
}

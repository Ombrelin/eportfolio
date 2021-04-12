package com.arsene.eportfolio.controllers;

import com.arsene.eportfolio.exceptions.ResourceNotFoundException;
import com.arsene.eportfolio.model.data.AbilityRepository;
import com.arsene.eportfolio.model.data.ProjectRepository;
import com.arsene.eportfolio.model.data.TechnologyRepository;
import com.arsene.eportfolio.model.dtos.ProjectDto;
import com.arsene.eportfolio.model.entities.Ability;
import com.arsene.eportfolio.model.entities.Project;
import com.arsene.eportfolio.model.entities.Technology;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private ProjectRepository projectRepository;
    private AbilityRepository abilityRepository;
    private TechnologyRepository technologyRepository;

    public ProjectController(ProjectRepository projectRepository, AbilityRepository abilityRepository, TechnologyRepository technologyRepository) {
        this.projectRepository = projectRepository;
        this.abilityRepository = abilityRepository;
        this.technologyRepository = technologyRepository;
    }

    @GetMapping
    public List<ProjectDto> findAll() {
        return projectRepository.findAll().stream().map(ProjectDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Project findById(@PathVariable("id") Integer id) {
        return projectRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        projectRepository.deleteById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto create(@RequestBody Project t) {
        var project = projectRepository.save(t);
        return new ProjectDto(project);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDto update(@PathVariable("id") Integer id, @RequestBody Project resource) {
        // TODO : check that query id and payload id are equal
        Project project = projectRepository.findById(resource.getId()).orElseThrow(ResourceNotFoundException::new);

        project.setName(resource.getName());
        project.setColor(resource.getColor());
        project.setDescription(resource.getDescription());
        project.setGit(resource.getGit());
        project.setIcon(resource.getIcon());

        projectRepository.save(resource);

        return new ProjectDto(project);
    }

    @PutMapping("/{id}/abilities/{abilityId}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDto addAbility(@PathVariable("id") Integer id, @PathVariable("abilityId") Integer abilityId) {
        var project = projectRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        var ability = abilityRepository.findById(abilityId).orElseThrow(ResourceNotFoundException::new);
        project.getAbilities().add(ability);
        projectRepository.save(project);

        return new ProjectDto(project);
    }

    @DeleteMapping("/{id}/abilities/{idAbility}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAbility(@PathVariable("id") Integer id, @PathVariable("idAbility") Integer idAbility) {
        Project p = projectRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        Ability ability = abilityRepository.findById(idAbility).orElseThrow(ResourceNotFoundException::new);
        p.getAbilities().remove(ability);
        projectRepository.save(p);
    }

    @PutMapping("/{id}/technologies/{idTech}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDto addTechnology(@PathVariable("id") Integer id, @PathVariable("idTech") Integer idTech) {
        var project = projectRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        var tech = technologyRepository.findById(idTech).orElseThrow(ResourceNotFoundException::new);
        project.getTechnologies().add(tech);
        projectRepository.save(project);
        return new ProjectDto(project);
    }

    @DeleteMapping("/{id}/technologies/{idTech}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTechnology(@PathVariable("id") Integer id, @PathVariable("idTech") Integer idTech) {
        Project p = projectRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        Technology t = technologyRepository.findById(idTech).orElseThrow(ResourceNotFoundException::new);
        p.getTechnologies().remove(t);
        projectRepository.save(p);
    }
}

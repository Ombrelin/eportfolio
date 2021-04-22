package com.arsene.eportfolio.controllers;

import com.arsene.eportfolio.model.dtos.CreateProjectDto;
import com.arsene.eportfolio.model.dtos.ProjectDto;
import com.arsene.eportfolio.model.dtos.UpdateProjectDto;
import com.arsene.eportfolio.services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<ProjectDto> findAll() {
        return projectService.getProjects();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        projectService.deleteProject(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto create(@RequestBody CreateProjectDto dto) {
        return projectService.createProject(dto);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProjectDto> update(@PathVariable("id") Integer id, @RequestBody UpdateProjectDto dto) {
        if (!id.equals(dto.getId())) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        return ResponseEntity.ok().body(projectService.updateProject(dto));
    }

    @PutMapping("/{id}/abilities/{abilityId}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDto addAbility(@PathVariable("id") Integer id, @PathVariable("abilityId") Integer abilityId) {
        return projectService.addAbilityToProject(id, abilityId);
    }

    @DeleteMapping("/{id}/abilities/{idAbility}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAbility(@PathVariable("id") Integer id, @PathVariable("idAbility") Integer idAbility) {
        projectService.removeAbilityFromProject(id, idAbility);
    }

    @PutMapping("/{id}/technologies/{idTech}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDto addTechnology(@PathVariable("id") Integer id, @PathVariable("idTech") Integer idTech) {
        return projectService.addTechnologyToProject(id, idTech);
    }

    @DeleteMapping("/{id}/technologies/{idTech}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTechnology(@PathVariable("id") Integer id, @PathVariable("idTech") Integer idTech) {
        projectService.removeTechnologyFromProject(id, idTech);
    }
}

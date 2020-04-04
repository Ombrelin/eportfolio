package com.arsene.eportfolio.controllers;

import com.arsene.eportfolio.exceptions.ResourceNotFoundException;
import com.arsene.eportfolio.model.data.AbilityRepository;
import com.arsene.eportfolio.model.data.ProjectRepository;
import com.arsene.eportfolio.model.data.TechnologyRepository;
import com.arsene.eportfolio.model.entities.Ability;
import com.arsene.eportfolio.model.entities.Project;
import com.arsene.eportfolio.model.entities.Technology;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectController {

    ProjectRepository projectRepository;
    AbilityRepository abilityRepository;
    TechnologyRepository technologyRepository;


    @GetMapping("/")
    public Iterable<Project> findAll() {
        return projectRepository.findAll();
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

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Project create(@RequestBody Project t) {
        return projectRepository.save(t);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Integer id, @RequestBody Project resource) {
        Optional<Project> t = projectRepository.findById(resource.getId());
        if (!t.isPresent()) {
            throw new ResourceNotFoundException();
        }
        projectRepository.save(resource);
    }

    @PostMapping("/{id}/abilities")
    @ResponseStatus(HttpStatus.OK)
    public void addAbility(@PathVariable("id") Integer id, @RequestBody Ability ability) {
        Project p = projectRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        p.getAbilities().add(ability);
        projectRepository.save(p);
    }

    @DeleteMapping("/{id}/abilities/{idAbility}")
    @ResponseStatus(HttpStatus.OK)
    public void removeAbility(@PathVariable("id") Integer id, @PathVariable("idAbility") Integer idAbility) {
        Project p = projectRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        Ability ability = abilityRepository.findById(idAbility).orElseThrow(ResourceNotFoundException::new);
        p.getAbilities().remove(ability);
        projectRepository.save(p);
    }

    @PostMapping("/{id}/technologies")
    @ResponseStatus(HttpStatus.OK)
    public void addTechnology(@PathVariable("id") Integer id, @RequestBody Technology t) {
        Project p = projectRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        p.getTechnologies().add(t);
        projectRepository.save(p);
    }

    @DeleteMapping("/{id}/technologies/{idTech}")
    @ResponseStatus(HttpStatus.OK)
    public void removeTechnology(@PathVariable("id") Integer id, @PathVariable("idTech") Integer idTech) {
        Project p = projectRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        Technology t = technologyRepository.findById(idTech).orElseThrow(ResourceNotFoundException::new);
        p.getTechnologies().remove(t);
        projectRepository.save(p);
    }
}

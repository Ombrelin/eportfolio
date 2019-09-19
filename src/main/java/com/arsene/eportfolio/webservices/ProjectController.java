package com.arsene.eportfolio.webservices;

import com.arsene.eportfolio.exceptions.ResourceNotFoundException;
import com.arsene.eportfolio.model.data.AbilityRepository;
import com.arsene.eportfolio.model.data.ProjectRepository;
import com.arsene.eportfolio.model.data.TechnologyRepository;
import com.arsene.eportfolio.model.entities.Ability;
import com.arsene.eportfolio.model.entities.Project;
import com.arsene.eportfolio.model.entities.Technology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProjectController {
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    AbilityRepository abilityRepository;

    @Autowired
    TechnologyRepository technologyRepository;


    @GetMapping("/")
    public Iterable<Project> findAll() {
        return projectRepository.findAll();
    }

    @GetMapping("/{id}")
    public Project findById(@PathVariable("id") Integer id) {
        Optional<Project> t = projectRepository.findById(id);
        if (!t.isPresent()) {
            throw new ResourceNotFoundException();
        }
        return t.get();
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
        Optional<Project> t = projectRepository.findById(id);
        if (!t.isPresent()) {
            throw new ResourceNotFoundException();
        }
        Project p = t.get();
        p.getAbilities().add(ability);
        projectRepository.save(p);
    }

    @DeleteMapping("/{id}/abilities/{idAbility}")
    @ResponseStatus(HttpStatus.OK)
    public void removeAbility(@PathVariable("id") Integer id, @PathVariable("idAbility") Integer idAbility) {
        Optional<Project> t = projectRepository.findById(id);
        if (!t.isPresent()) {
            throw new ResourceNotFoundException();
        }
        Project p = t.get();
        Optional<Ability> a = abilityRepository.findById(idAbility);
        if (!a.isPresent()) {
            throw new ResourceNotFoundException();
        }
        Ability ability = a.get();
        p.getAbilities().remove(ability);
        projectRepository.save(p);
    }

    @PostMapping("/{id}/technologies")
    @ResponseStatus(HttpStatus.OK)
    public void addTechnology(@PathVariable("id") Integer id, @RequestBody Technology t) {
        Optional<Project> opt = projectRepository.findById(id);
        if (!opt.isPresent()) {
            throw new ResourceNotFoundException();
        }
        Project p = opt.get();
        p.getTechnologies().add(t);
        projectRepository.save(p);
    }

    @DeleteMapping("/{id}/technologies/{idTech}")
    @ResponseStatus(HttpStatus.OK)
    public void removeTechnology(@PathVariable("id") Integer id, @PathVariable("idTech") Integer idTech) {
        Optional<Project> opt = projectRepository.findById(id);
        if (!opt.isPresent()) {
            throw new ResourceNotFoundException();
        }
        Optional<Technology> optTech = technologyRepository.findById(idTech);
        if (!optTech.isPresent()) {
            throw new ResourceNotFoundException();
        }
        Project p = opt.get();
        Technology t = optTech.get();
        p.getTechnologies().remove(t);
        projectRepository.save(p);
    }

}

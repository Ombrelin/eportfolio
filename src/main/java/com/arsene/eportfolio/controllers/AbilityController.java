package com.arsene.eportfolio.controllers;

import com.arsene.eportfolio.exceptions.ResourceNotFoundException;
import com.arsene.eportfolio.model.data.AbilityRepository;
import com.arsene.eportfolio.model.data.SubjectRepository;
import com.arsene.eportfolio.model.data.TechnologyRepository;
import com.arsene.eportfolio.model.entities.Ability;
import com.arsene.eportfolio.model.entities.Subject;
import com.arsene.eportfolio.model.entities.Technology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/abilities")
public class AbilityController {

    @Autowired
    AbilityRepository abilityRepository;

    @Autowired
    TechnologyRepository technologyRepository;

    @Autowired
    SubjectRepository subjectRepository;


    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Ability> findAll() {
        return abilityRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Ability findById(@PathVariable("id") Integer id) {
        Optional<Ability> t = abilityRepository.findById(id);
        if (!t.isPresent()) {
            throw new ResourceNotFoundException();
        }
        return t.get();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        Optional<Ability> t = abilityRepository.findById(id);
        if (!t.isPresent()) {
            throw new ResourceNotFoundException();
        }
        Ability a = t.get();

        for (Subject s : subjectRepository.findAll()) {
            if (s.getAbilities().contains(a)) {
                s.getAbilities().remove(a);
                subjectRepository.save(s);
            }
        }

    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Ability create(@RequestBody Ability t) {
        return abilityRepository.save(t);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Ability update(@PathVariable("id") Integer id, @RequestBody Ability resource) {
        Optional<Ability> t = abilityRepository.findById(resource.getId());
        if (!t.isPresent()) {
            throw new ResourceNotFoundException();
        }
        abilityRepository.save(resource);
        return resource;
    }

    @PostMapping("/{id}/technologies")
    @ResponseStatus(HttpStatus.OK)
    public Technology addTechnology(@PathVariable("id") Integer id, @RequestBody Technology t) {
        Optional<Ability> optionalAbility = abilityRepository.findById(id);
        if (!optionalAbility.isPresent()) {
            throw new ResourceNotFoundException();
        }
        Ability ability = optionalAbility.get();
        technologyRepository.save(t);
        ability.getTechnologies().add(t);
        abilityRepository.save(ability);
        return t;
    }
}
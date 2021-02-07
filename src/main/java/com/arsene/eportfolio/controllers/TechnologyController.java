package com.arsene.eportfolio.controllers;

import com.arsene.eportfolio.exceptions.ResourceNotFoundException;
import com.arsene.eportfolio.model.data.AbilityRepository;
import com.arsene.eportfolio.model.data.TechnologyRepository;
import com.arsene.eportfolio.model.entities.Ability;
import com.arsene.eportfolio.model.entities.Technology;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/technologies")
public class TechnologyController {

    TechnologyRepository technologyRepository;
    AbilityRepository abilityRepository;

    @GetMapping("/")
    public Iterable<Technology> findAll() {
        return technologyRepository.findAll();
    }

    @GetMapping("/{id}")
    public Technology findById(@PathVariable("id") Integer id) {
        Optional<Technology> t = technologyRepository.findById(id);
        if (!t.isPresent()) {
            throw new ResourceNotFoundException();
        }
        return t.get();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        Optional<Technology> opt = technologyRepository.findById(id);
        if (!opt.isPresent()) {
            throw new ResourceNotFoundException();
        }
        Technology t = opt.get();

        for (Ability a : abilityRepository.findAll()) {
            if (a.getTechnologies().contains(t)) {
                a.getTechnologies().remove(t);
                abilityRepository.save(a);
            }
        }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Technology create(@RequestBody Technology t) {

        return technologyRepository.save(t);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Integer id, @RequestBody Technology resource) {
        Optional<Technology> t = technologyRepository.findById(id);
        System.out.println("Techno : " + t.get());
        if (!t.isPresent()) {
            throw new ResourceNotFoundException();
        }
        technologyRepository.save(resource);
    }
}

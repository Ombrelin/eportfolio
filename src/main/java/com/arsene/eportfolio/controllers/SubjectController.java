package com.arsene.eportfolio.controllers;

import com.arsene.eportfolio.exceptions.ResourceNotFoundException;
import com.arsene.eportfolio.model.data.AbilityRepository;
import com.arsene.eportfolio.model.data.SubjectRepository;
import com.arsene.eportfolio.model.dtos.AbilityDto;
import com.arsene.eportfolio.model.entities.Ability;
import com.arsene.eportfolio.model.entities.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final Logger logger = LoggerFactory.getLogger(SubjectController.class);

    private SubjectRepository subjectRepository;
    private AbilityRepository abilityRepository;

    public SubjectController(SubjectRepository subjectRepository, AbilityRepository abilityRepository) {
        this.subjectRepository = subjectRepository;
        this.abilityRepository = abilityRepository;
    }

    @GetMapping
    public Iterable<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @GetMapping("/{id}")
    public Subject findById(@PathVariable("id") Integer id) {
        return subjectRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        subjectRepository.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Subject create(@RequestBody Subject t) {
        return subjectRepository.save(t);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Integer id, @RequestBody Subject resource) {
        Optional<Subject> t = subjectRepository.findById(resource.getId());
        if (!t.isPresent()) {
            throw new ResourceNotFoundException();
        }
        subjectRepository.save(resource);
    }

    @PostMapping("/{id}/abilities")
    public Ability addAbility(@PathVariable("id") Integer id, @RequestBody Ability resource) {
        Optional<Subject> t = subjectRepository.findById(id);
        if (!t.isPresent()) {
            throw new ResourceNotFoundException();
        }
        Subject subject = t.get();
        abilityRepository.save(resource);
        subject.getAbilities().add(resource);
        subjectRepository.save(subject);

        return resource;
    }

    @GetMapping("/{subjectId}/abilities/{abilityId}")
    @ResponseStatus(HttpStatus.OK)
    public AbilityDto getAbility(@PathVariable("subjectId") Integer subjectId, @PathVariable("abilityId") Integer abilityId) {
        var ability = abilityRepository.findById(abilityId).orElseThrow(ResourceNotFoundException::new);

        if (ability.getSubject().getId() == subjectId) {
            return new AbilityDto(ability.getId(), ability.getName(), ability.getColor(), ability.getImage(), ability.getTechnologies());
        }
        throw new ResourceNotFoundException();
    }

}

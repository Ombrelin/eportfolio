package com.arsene.eportfolio.controllers;

import com.arsene.eportfolio.exceptions.ResourceNotFoundException;
import com.arsene.eportfolio.model.data.DiplomaRepository;
import com.arsene.eportfolio.model.entities.Diploma;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/diplomas")
public class DiplomaController {

    private final DiplomaRepository diplomaRepository;

    public DiplomaController(DiplomaRepository diplomaRepository) {
        this.diplomaRepository = diplomaRepository;
    }

    @GetMapping
    public Iterable<Diploma> findAll() {
        return diplomaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Diploma findById(@PathVariable("id") Integer id) {
        return diplomaRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        diplomaRepository.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Diploma create(@RequestBody Diploma t) {
        return diplomaRepository.save(t);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Integer id, @RequestBody Diploma resource) {
        Optional<Diploma> t = diplomaRepository.findById(resource.getId());
        if (!t.isPresent()) {
            throw new ResourceNotFoundException();
        }
        diplomaRepository.save(resource);
    }

}

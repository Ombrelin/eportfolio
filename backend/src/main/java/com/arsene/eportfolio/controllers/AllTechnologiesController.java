package com.arsene.eportfolio.controllers;

import com.arsene.eportfolio.model.data.TechnologyRepository;
import com.arsene.eportfolio.model.dtos.TechnologyDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/technologies")
public class AllTechnologiesController {

    private final TechnologyRepository technologyRepository;

    public AllTechnologiesController(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    @GetMapping
    public List<TechnologyDto> findAll() {
        return this.technologyRepository
                .findAll()
                .stream()
                .map(TechnologyDto::new)
                .collect(Collectors.toList());
    }


}

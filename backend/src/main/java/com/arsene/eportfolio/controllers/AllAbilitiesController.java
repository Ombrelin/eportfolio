package com.arsene.eportfolio.controllers;

import com.arsene.eportfolio.model.data.AbilityRepository;
import com.arsene.eportfolio.model.dtos.AbilityDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/abilities")
public class AllAbilitiesController {

    private final AbilityRepository abilityRepository;

    public AllAbilitiesController(AbilityRepository abilityRepository) {
        this.abilityRepository = abilityRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AbilityDto> findAll() {
        return abilityRepository
                .findByOrderByIdAsc()
                .stream()
                .map(AbilityDto::new)
                .collect(Collectors.toList());
    }


}

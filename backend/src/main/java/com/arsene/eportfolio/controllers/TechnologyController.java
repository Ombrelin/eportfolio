package com.arsene.eportfolio.controllers;

import com.arsene.eportfolio.exceptions.ResourceNotFoundException;
import com.arsene.eportfolio.model.data.AbilityRepository;
import com.arsene.eportfolio.model.data.TechnologyRepository;
import com.arsene.eportfolio.model.dtos.CreateTechnologyDto;
import com.arsene.eportfolio.model.dtos.TechnologyDto;
import com.arsene.eportfolio.model.entities.Technology;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subjects/{subjectId}/abilities/{abilityId}/technologies")
public class TechnologyController {

    private final TechnologyRepository technologyRepository;
    private final AbilityRepository abilityRepository;

    public TechnologyController(TechnologyRepository technologyRepository, AbilityRepository abilityRepository) {
        this.technologyRepository = technologyRepository;
        this.abilityRepository = abilityRepository;
    }

    @DeleteMapping("{technologyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("technologyId") Integer technologyId, @PathVariable("subjectId") Integer subjectId, @PathVariable("abilityId") Integer abilityId) {
        Technology tech = technologyRepository
                .findBySubjectAndAbility(subjectId, abilityId, technologyId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No technology with Id : %s for subject with Id : %s and ability %s", technologyId, subjectId, abilityId)));

        technologyRepository.delete(tech);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TechnologyDto create(@RequestBody CreateTechnologyDto dto,
                                @PathVariable("subjectId") Integer subjectId, @PathVariable("abilityId") Integer abilityId) {

        var ability = abilityRepository
                .getAbilityBySubjectId(subjectId, abilityId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No ability with Id : %s for subject with Id : %s", abilityId, subjectId)));

        var tech = new Technology(dto.getName(), dto.getImage(), ability);
        technologyRepository.save(tech);

        return new TechnologyDto(tech);
    }

    @PutMapping("{technologyId}")
    @ResponseStatus(HttpStatus.OK)
    public TechnologyDto update(@RequestBody TechnologyDto dto,
                                @PathVariable("technologyId") Integer technologyId,
                                @PathVariable("subjectId") Integer subjectId,
                                @PathVariable("abilityId") Integer abilityId) {

        Technology tech = technologyRepository
                .findBySubjectAndAbility(subjectId, abilityId, technologyId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No technology with Id : %s for subject with Id : %s and ability %s", technologyId, subjectId, abilityId)));

        tech.setName(dto.getName());
        tech.setImage(dto.getImage());

        technologyRepository.save(tech);

        return new TechnologyDto(tech);
    }
}

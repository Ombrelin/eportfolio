package com.arsene.eportfolio.controllers;

import com.arsene.eportfolio.exceptions.ResourceNotFoundException;
import com.arsene.eportfolio.model.data.AbilityRepository;
import com.arsene.eportfolio.model.data.SubjectRepository;
import com.arsene.eportfolio.model.data.TechnologyRepository;
import com.arsene.eportfolio.model.dtos.AbilityDto;
import com.arsene.eportfolio.model.dtos.CreateAbilityDto;
import com.arsene.eportfolio.model.dtos.UpdateAbilityDto;
import com.arsene.eportfolio.model.entities.Ability;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subjects/{subjectId}/abilities")
public class AbilityController {

    private final AbilityRepository abilityRepository;
    private final TechnologyRepository technologyRepository;
    private final SubjectRepository subjectRepository;

    public AbilityController(AbilityRepository abilityRepository, TechnologyRepository technologyRepository, SubjectRepository subjectRepository) {
        this.abilityRepository = abilityRepository;
        this.technologyRepository = technologyRepository;
        this.subjectRepository = subjectRepository;
    }

    @GetMapping("{abilityId}")
    @ResponseStatus(HttpStatus.OK)
    public AbilityDto getAbility(@PathVariable("subjectId") Integer subjectId, @PathVariable("abilityId") Integer abilityId) {
        var ability = abilityRepository
                .getAbilityBySubjectId(subjectId, abilityId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No ability with id : %s", abilityId)));
        return new AbilityDto(ability);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AbilityDto getAbility(@PathVariable("subjectId") Integer subjectId, @RequestBody CreateAbilityDto abilityDto) {

        var subject = subjectRepository
                .findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No subject with id : %s", subjectId)));

        var ability = new Ability(abilityDto.getName(), abilityDto.getColor(), abilityDto.getImage(), subject);
        abilityRepository.save(ability);

        return new AbilityDto(ability);
    }

    @DeleteMapping("{abilityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAbility(@PathVariable("subjectId") Integer subjectId, @PathVariable("abilityId") Integer abilityId) {
        if (!abilityRepository.existsAbilityBySubjectId(subjectId, abilityId)) {
            throw new ResourceNotFoundException(String.format("No ability with Id : %s", abilityId));
        }

        abilityRepository.deleteById(abilityId);
    }

    @PutMapping("{abilityId}")
    @ResponseStatus(HttpStatus.OK)
    public AbilityDto updateAbility(@PathVariable("subjectId") Integer subjectId, @PathVariable("abilityId") Integer abilityId, @RequestBody UpdateAbilityDto dto) {
        if (!(abilityId.equals(dto.getId())
                && abilityRepository.existsAbilityBySubjectId(subjectId, abilityId))) {
            throw new ResourceNotFoundException(String.format("No ability with Id : %s", abilityId));
        }

        var ability = abilityRepository.findById(abilityId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No ability with Id : %s", abilityId)));

        ability.setName(dto.getName());
        ability.setColor(dto.getColor());
        ability.setImage(dto.getImage());

        abilityRepository.save(ability);
        return new AbilityDto(ability);
    }
}

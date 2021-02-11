package com.arsene.eportfolio.controllers;

import com.arsene.eportfolio.exceptions.ResourceNotFoundException;
import com.arsene.eportfolio.model.data.AbilityRepository;
import com.arsene.eportfolio.model.data.SubjectRepository;
import com.arsene.eportfolio.model.dtos.*;
import com.arsene.eportfolio.model.entities.Ability;
import com.arsene.eportfolio.model.entities.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private SubjectRepository subjectRepository;
    private AbilityRepository abilityRepository;

    public SubjectController(SubjectRepository subjectRepository, AbilityRepository abilityRepository) {
        this.subjectRepository = subjectRepository;
        this.abilityRepository = abilityRepository;
    }

    @GetMapping
    public List<SubjectDto> findAll() {
        return subjectRepository
                .findByOrderByIdAsc()
                .stream()
                .map(SubjectDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SubjectDto findById(@PathVariable("id") Integer id) {
        var subject = subjectRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return new SubjectDto(subject);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        subjectRepository.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectDto create(@RequestBody CreateSubjectDto dto) {
        var subject = new Subject(dto.getName(), dto.getIcon(), dto.getImage());
        subjectRepository.save(subject);
        return new SubjectDto(subject);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SubjectDto update(@PathVariable("id") Integer id, @RequestBody UpdateSubjectDto dto) {
        if (!(id.equals(dto.getId()) && subjectRepository.existsById(id))) {
            throw new ResourceNotFoundException(String.format("No subject with id : %s", id));
        }
        var subject = subjectRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No subject with id : %s", id)));

        subject.setName(dto.getName());
        subject.setImage(dto.getImage());
        subject.setIcon(dto.getIcon());

        subjectRepository.save(subject);

        return new SubjectDto(subject);
    }

    @GetMapping("/{subjectId}/abilities/{abilityId}")
    @ResponseStatus(HttpStatus.OK)
    public AbilityDto getAbility(@PathVariable("subjectId") Integer subjectId, @PathVariable("abilityId") Integer abilityId) {
        var ability = abilityRepository
                .getAbilityBySubjectId(subjectId, abilityId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No ability with id : %s", abilityId)));
        return new AbilityDto(ability);
    }

    @PostMapping("/{subjectId}/abilities")
    @ResponseStatus(HttpStatus.CREATED)
    public AbilityDto getAbility(@PathVariable("subjectId") Integer subjectId, @RequestBody CreateAbilityDto abilityDto) {

        var subject = subjectRepository
                .findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No subject with id : %s", subjectId)));

        var ability = new Ability(abilityDto.getName(), abilityDto.getColor(), abilityDto.getImage(), subject);
        abilityRepository.save(ability);

        return new AbilityDto(ability);
    }

    @DeleteMapping("/{subjectId}/abilities/{abilityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAbility(@PathVariable("subjectId") Integer subjectId, @PathVariable("abilityId") Integer abilityId) {
        if (!abilityRepository.existsAbilityBySubjectId(subjectId, abilityId)) {
            throw new ResourceNotFoundException(String.format("No ability with Id : %s", abilityId));
        }

        abilityRepository.deleteById(abilityId);
    }

    @PutMapping("/{subjectId}/abilities/{abilityId}")
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

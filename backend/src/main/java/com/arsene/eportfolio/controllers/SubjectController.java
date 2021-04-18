package com.arsene.eportfolio.controllers;

import com.arsene.eportfolio.exceptions.ResourceNotFoundException;
import com.arsene.eportfolio.model.data.AbilityRepository;
import com.arsene.eportfolio.model.data.SubjectRepository;
import com.arsene.eportfolio.model.dtos.CreateSubjectDto;
import com.arsene.eportfolio.model.dtos.SubjectDto;
import com.arsene.eportfolio.model.dtos.UpdateSubjectDto;
import com.arsene.eportfolio.model.entities.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectRepository subjectRepository;
    private final AbilityRepository abilityRepository;

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


}

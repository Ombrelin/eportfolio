package com.arsene.eportfolio.controllers;

import com.arsene.eportfolio.exceptions.ResourceNotFoundException;
import com.arsene.eportfolio.model.data.AbilityRepository;
import com.arsene.eportfolio.model.data.SubjectRepository;
import com.arsene.eportfolio.model.data.TechnologyRepository;
import com.arsene.eportfolio.model.dtos.AbilityDto;
import com.arsene.eportfolio.model.entities.Ability;
import com.arsene.eportfolio.model.entities.Technology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/abilities")
public class AbilityController {

    private final Logger logger = LoggerFactory.getLogger(AbilityController.class);

    private final AbilityRepository abilityRepository;
    private final TechnologyRepository technologyRepository;
    private final SubjectRepository subjectRepository;

    public AbilityController(AbilityRepository abilityRepository, TechnologyRepository technologyRepository, SubjectRepository subjectRepository) {
        this.abilityRepository = abilityRepository;
        this.technologyRepository = technologyRepository;
        this.subjectRepository = subjectRepository;
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

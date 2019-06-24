package com.arsene.eportfolio.webservices;

import com.arsene.eportfolio.exceptions.ResourceNotFoundException;
import com.arsene.eportfolio.model.data.AbilityRepository;
import com.arsene.eportfolio.model.data.TechnologyRepository;
import com.arsene.eportfolio.model.entities.Ability;
import com.arsene.eportfolio.model.entities.Technology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/abilities")
@CrossOrigin(origins = {"http://localhost:4200", "https://arsenelapostolet.fr"})
public class AbilityController {

    @Autowired
    AbilityRepository abilityRepository;

    @Autowired
    TechnologyRepository technologyRepository;


    @GetMapping("/")
    public Iterable<Ability> findAll(){
        return abilityRepository.findAll();
    }

    @GetMapping("/{id}")
    public Ability findById(@PathVariable("id") Integer id){
        Optional<Ability> t = abilityRepository.findById(id);
        if(!t.isPresent()){
            throw new ResourceNotFoundException();
        }
        return t.get();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id){
        abilityRepository.deleteById(id);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Ability create(@RequestBody Ability t){
        return abilityRepository.save(t);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) Integer id, @RequestBody Ability resource) {
        Optional<Ability> t = abilityRepository.findById(resource.getId());
        if(!t.isPresent()){
            throw new ResourceNotFoundException();
        }
        System.out.println(resource);
        abilityRepository.save(resource);
    }

    @PostMapping("/{id}/technologies")
    public Technology addTechnology(@PathVariable("id") Integer id, @RequestBody Technology t){
        Optional<Ability> optionalAbility = abilityRepository.findById(id);
        if(!optionalAbility.isPresent()){
            throw new ResourceNotFoundException();
        }
        Ability ability = optionalAbility.get();
        technologyRepository.save(t);
        ability.getTechnologies().add(t);
        abilityRepository.save(ability);
        return t;
    }
}

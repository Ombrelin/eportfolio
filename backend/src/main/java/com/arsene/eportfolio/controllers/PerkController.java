package com.arsene.eportfolio.controllers;

import com.arsene.eportfolio.model.entities.Perk;
import com.arsene.eportfolio.services.PerkService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perks")
public class PerkController {

    private final PerkService perkService;

    public PerkController(PerkService perkService) {
        this.perkService = perkService;
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Perk> getAll() {
        return perkService.getAll();
    }

    @GetMapping({"/{id}"})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Perk get(@PathVariable String id) {
        return perkService.get(id);
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Perk create(@RequestBody Perk perk) {
        return perkService.create(perk);
    }

    @PutMapping({"/{id}"})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Perk update(@RequestBody Perk perk, @PathVariable String id) {
        return perkService.update(id, perk);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id) {
        perkService.delete(id);
    }
}
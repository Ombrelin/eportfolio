package com.arsene.eportfolio.controllers;

import com.arsene.eportfolio.model.entities.WorkExperience;
import com.arsene.eportfolio.services.WorkExperienceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WorkExperienceController.BASE_URL)
@AllArgsConstructor
public class WorkExperienceController {

    public static final String BASE_URL = "/experiences";

    private final WorkExperienceService workExperienceService;

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<WorkExperience> getAll() {
        return workExperienceService.getAll();
    }

    @GetMapping({"/{id}"})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public WorkExperience get(@PathVariable String id) {
        return workExperienceService.get(id);
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public WorkExperience create(@RequestBody WorkExperience WorkExperience) {
        return workExperienceService.create(WorkExperience);
    }

    @PutMapping({"/{id}"})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public WorkExperience update(@RequestBody WorkExperience WorkExperience, @PathVariable String id) {
        return workExperienceService.update(id, WorkExperience);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id) {
        workExperienceService.delete(id);
    }
}
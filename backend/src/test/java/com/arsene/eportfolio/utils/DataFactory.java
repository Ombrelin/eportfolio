package com.arsene.eportfolio.utils;

import com.arsene.eportfolio.model.dtos.CreateProjectDto;
import com.arsene.eportfolio.model.entities.Ability;
import com.arsene.eportfolio.model.entities.Project;
import com.arsene.eportfolio.model.entities.Subject;
import com.arsene.eportfolio.model.entities.Technology;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataFactory {

    public static Subject createSubject() {
        return new Subject("test subject name");
    }

    public static Ability createAbility(Subject subject) {
        return new Ability("test ability name", "test ability color", "test ability image", subject);
    }

    public static Ability createAbility() {
        return new Ability("test ability name", "test ability color", "test ability image", createSubject());
    }

    public static Ability createAbilityWithId() {
        return new Ability(1, "test ability name", "test ability color", "test ability image", createSubject());
    }

    public static Project createProject() {
        return new Project("test project 1 name", "test project 1 icon", "test project 1 description", "test project 1 git", "test project 1 color");
    }

    public static List<Project> createProjects(Integer i) {
        return IntStream.rangeClosed(1, i)
                .mapToObj(number -> new Project(
                        String.format("test project %d name", number),
                        String.format("test project %d icon", number),
                        String.format("test project %d description", number),
                        String.format("test project %d git", number),
                        String.format("test project %d color", number)))
                .collect(Collectors.toList());
    }

    public static List<Project> createProjectsWithId(Integer i) {
        List<Project> projects = createProjects(i);
        for (Project project : projects) {
            project.setId(projects.indexOf(project));
        }
        return projects;
    }

    public static CreateProjectDto createProjectDto(Integer abilityId, Integer technologyId) {
        return new CreateProjectDto(
                "test project 1 name",
                "test project 1 icon",
                "test project 1 description",
                "test project 1 git",
                "test project 1 color",
                List.of(abilityId),
                List.of(technologyId)
        );
    }

    public static Technology createTechnology(Ability ability) {
        return new Technology("test tech name", "test tech image", ability);
    }


    public static Technology createTechnologyWithId(Ability ability) {
        return new Technology(1, "test tech name", "test tech image", ability);
    }

    public static Technology createTechnologyWithId() {
        return new Technology(1, "test tech name", "test tech image", createAbility());
    }

    public static Project createProjectWithId() {
        return new Project(1, "test project 1 name", "test project 1 icon", "test project 1 description", "test project 1 git", "test project 1 color");
    }
}

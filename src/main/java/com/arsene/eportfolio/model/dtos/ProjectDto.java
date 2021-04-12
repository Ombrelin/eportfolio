package com.arsene.eportfolio.model.dtos;

import com.arsene.eportfolio.model.entities.Ability;
import com.arsene.eportfolio.model.entities.Project;
import com.arsene.eportfolio.model.entities.Subject;
import com.arsene.eportfolio.model.entities.Technology;

import java.util.Set;
import java.util.stream.Collectors;

public class ProjectDto {
    private Integer id;
    private String name;
    private String icon;
    private String description;
    private String git;
    private String color;
    private Set<TechnologyDto> technologies;
    private Set<AbilityDto> abilities;

    public ProjectDto(Integer id, String name, String icon, String description, String git, String color, Set<TechnologyDto> technologies, Set<AbilityDto> abilities, Subject subject) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.git = git;
        this.color = color;
        this.technologies = technologies;
        this.abilities = abilities;
    }

    public ProjectDto(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.icon = project.getIcon();
        this.description = project.getDescription();
        this.git = project.getGit();
        this.color = project.getColor();
        this.technologies = project.getTechnologies().stream().map(TechnologyDto::new).collect(Collectors.toSet());
        this.abilities = project.getAbilities().stream().map(AbilityDto::new).collect(Collectors.toSet());

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGit() {
        return git;
    }

    public void setGit(String git) {
        this.git = git;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Set<TechnologyDto> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(Set<TechnologyDto> technologies) {
        this.technologies = technologies;
    }

    public Set<AbilityDto> getAbilities() {
        return abilities;
    }

    public void setAbilities(Set<AbilityDto> abilities) {
        this.abilities = abilities;
    }
}

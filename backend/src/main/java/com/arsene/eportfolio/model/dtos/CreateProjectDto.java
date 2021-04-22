package com.arsene.eportfolio.model.dtos;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class CreateProjectDto {

    @NotBlank
    private String name;

    @NotBlank
    private String icon;

    @NotBlank
    private String description;

    @NotBlank
    private String git;

    @NotBlank
    private String color;

    private List<Integer> abilities;
    private List<Integer> technologies;

    public CreateProjectDto() {
    }

    public CreateProjectDto(String name, String icon, String description, String git, String color, List<Integer> abilities, List<Integer> technologies) {
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.git = git;
        this.color = color;
        this.abilities = abilities;
        this.technologies = technologies;
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

    public List<Integer> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Integer> abilities) {
        this.abilities = abilities;
    }

    public List<Integer> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<Integer> technologies) {
        this.technologies = technologies;
    }
}

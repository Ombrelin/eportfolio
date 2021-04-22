package com.arsene.eportfolio.model.dtos;

import javax.validation.constraints.NotBlank;

public class UpdateProjectDto {
    @NotBlank
    private Integer id;

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

    public UpdateProjectDto() {
    }

    public UpdateProjectDto(Integer id, String name, String icon, String description, String git, String color) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.git = git;
        this.color = color;
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
}

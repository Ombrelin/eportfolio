package com.arsene.eportfolio.model.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateAbilityDto {

    @NotNull
    @NotBlank
    private Integer id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String color;

    @NotNull
    @NotBlank
    private String image;

    public UpdateAbilityDto() {
    }

    public UpdateAbilityDto(@NotNull @NotBlank Integer id, @NotNull @NotBlank String name, @NotNull @NotBlank String color, @NotNull @NotBlank String image) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.image = image;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

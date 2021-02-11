package com.arsene.eportfolio.model.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateAbilityDto {
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String color;

    @NotNull
    @NotBlank
    private String image;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

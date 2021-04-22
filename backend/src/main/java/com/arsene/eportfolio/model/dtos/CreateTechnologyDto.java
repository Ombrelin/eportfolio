package com.arsene.eportfolio.model.dtos;

import javax.validation.constraints.NotBlank;

public class CreateTechnologyDto {

    @NotBlank
    private String name;

    @NotBlank
    private String image;

    public CreateTechnologyDto() {
    }

    public CreateTechnologyDto(@NotBlank String name, @NotBlank String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

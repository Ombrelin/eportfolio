package com.arsene.eportfolio.model.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateSubjectDto {

    @NotBlank
    private String name;

    @NotBlank
    private String icon;

    @NotBlank
    private String image;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

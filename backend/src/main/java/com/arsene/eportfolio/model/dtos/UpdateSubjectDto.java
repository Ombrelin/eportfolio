package com.arsene.eportfolio.model.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateSubjectDto {

    @NotNull
    @NotBlank
    private Integer id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String icon;

    @NotNull
    @NotBlank
    private String image;

    public UpdateSubjectDto() {
    }

    public UpdateSubjectDto(@NotNull @NotBlank Integer id, @NotNull @NotBlank String name, @NotNull @NotBlank String icon, @NotNull @NotBlank String image) {
        this.id = id;
        this.name = name;
        this.icon = icon;
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

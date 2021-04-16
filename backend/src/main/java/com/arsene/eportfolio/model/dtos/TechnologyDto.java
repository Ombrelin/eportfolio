package com.arsene.eportfolio.model.dtos;

import com.arsene.eportfolio.model.entities.Technology;

public class TechnologyDto {

    private Integer id;
    private String name;
    private String image;

    public TechnologyDto() {
    }

    public TechnologyDto(Technology technology) {
        this.id = technology.getId();
        this.name = technology.getName();
        this.image = technology.getImage();
    }

    public TechnologyDto(Integer id, String name, String image) {
        this.id = id;
        this.name = name;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

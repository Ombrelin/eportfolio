package com.arsene.eportfolio.model.dtos;

import com.arsene.eportfolio.model.entities.Technology;

import java.util.Set;

public class AbilityDto {

    private Integer id;
    private String name;
    private String color;
    private String image;
    private Set<Technology> technologies;

    public AbilityDto(Integer id, String name, String color, String image, Set<Technology> technologies) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.image = image;
        this.technologies = technologies;
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

    public Set<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(Set<Technology> technologies) {
        this.technologies = technologies;
    }
}

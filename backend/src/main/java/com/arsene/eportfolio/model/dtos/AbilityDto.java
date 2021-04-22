package com.arsene.eportfolio.model.dtos;

import com.arsene.eportfolio.model.entities.Ability;

import java.util.Set;
import java.util.stream.Collectors;

public class AbilityDto {

    private Integer id;
    private String name;
    private String color;
    private String image;
    private Set<TechnologyDto> technologies;

    public AbilityDto(Ability ability) {
        this.id = ability.getId();
        this.name = ability.getName();
        this.color = ability.getColor();
        this.image = ability.getImage();
        this.technologies = ability.getTechnologies()
                .stream()
                .map(TechnologyDto::new)
                .collect(Collectors.toSet());
    }

    public AbilityDto(Integer id, String name, String color, String image, Set<TechnologyDto> technologies) {
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

    public Set<TechnologyDto> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(Set<TechnologyDto> technologies) {
        this.technologies = technologies;
    }
}

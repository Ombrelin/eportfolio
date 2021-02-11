package com.arsene.eportfolio.model.dtos;

import com.arsene.eportfolio.model.entities.Subject;

import java.util.Set;
import java.util.stream.Collectors;

public class SubjectDto {

    private Integer id;
    private String name;
    private String icon;
    private String image;
    private Set<AbilityDto> abilities;

    public SubjectDto(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.icon = subject.getIcon();
        this.image = subject.getImage();
        this.abilities = subject.getAbilities().stream().map(AbilityDto::new).collect(Collectors.toSet());
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

    public Set<AbilityDto> getAbilities() {
        return abilities;
    }

    public void setAbilities(Set<AbilityDto> abilities) {
        this.abilities = abilities;
    }
}

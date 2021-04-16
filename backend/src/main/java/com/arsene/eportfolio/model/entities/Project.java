package com.arsene.eportfolio.model.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String icon;
    private String description;
    private String git;
    private String color;


    @ManyToMany
    private Set<Technology> technologies;

    @ManyToMany
    private Set<Ability> abilities;

    public Project() {
    }

    public Project(Integer id, String name, String icon, String description, String git, String color) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.git = git;
        this.color = color;
        this.technologies = new HashSet<>();
        this.abilities = new HashSet<>();
    }

    public Project(Integer id, String name, String icon, String description, String git, String color, Subject subject) {
        this(id, name,icon,description,git,color);
        this.abilities = new HashSet<>();
    }

    public Project(String name, String icon, String description, String git, String color) {
        this(null, name,icon,description,git,color);
        this.abilities = new HashSet<>();
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

    public Set<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(Set<Technology> technologies) {
        this.technologies = technologies;
    }

    public Set<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(Set<Ability> abilities) {
        this.abilities = abilities;
    }

}
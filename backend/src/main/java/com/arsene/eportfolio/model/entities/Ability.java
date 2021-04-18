package com.arsene.eportfolio.model.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Ability {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String color;
    private String image;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    private Subject subject;

    @OneToMany(
            mappedBy = "ability",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<Technology> technologies;

    public Ability() {
    }

    public Ability(String name, String color, String image, Subject subject) {
        this.name = name;
        this.color = color;
        this.image = image;
        this.technologies = new HashSet<>();
        this.subject = subject;
    }

    public Ability(Integer id, String name, String color, String image, Subject subject) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.image = image;
        this.technologies = new HashSet<>();
        this.subject = subject;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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
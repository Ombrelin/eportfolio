package com.arsene.eportfolio.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
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

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Ability> abilities;

    @ManyToOne
    private Subject subject;


}
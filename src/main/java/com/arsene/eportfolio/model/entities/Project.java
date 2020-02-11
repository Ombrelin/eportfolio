package com.arsene.eportfolio.model.entities;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

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
    private List<Technology> technologies;

    @ManyToMany
    private List<Ability> abilities;

    @ManyToOne
    private Subject subject;


}
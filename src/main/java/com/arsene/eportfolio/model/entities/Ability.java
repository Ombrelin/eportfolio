package com.arsene.eportfolio.model.entities;

import lombok.Data;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Data
public class Ability {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String color;
    private String image;
    @OneToMany(targetEntity = Technology.class, orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Technology> technologies;
}
package com.arsene.eportfolio.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Ability {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String color;
    private String image;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Technology.class, orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Set<Technology> technologies;
}
package com.arsene.eportfolio.model.entities;

import javax.persistence.*;

@Entity
public class Technology {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String image;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    private Ability ability;

    public Technology() {
    }

    public Technology(String name, String image, Ability ability) {
        this.name = name;
        this.image = image;
        this.ability = ability;
    }

    public Technology(Integer id, String name, String image, Ability ability) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.ability = ability;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
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
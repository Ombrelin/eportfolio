package com.arsene.eportfolio.model.entities;

import javax.persistence.*;

@Entity
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String key;

    private String value;

    public Config() {
    }

    public Config(Integer id, String key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public Config(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

package com.arsene.eportfolio.services;

import com.arsene.eportfolio.model.entities.Perk;

import java.util.List;


public interface PerkService {

    Perk get(String id);

    List<Perk> getAll();

    Perk create(Perk dto);

    Perk update(String id, Perk dto);

    void delete(String id);

}

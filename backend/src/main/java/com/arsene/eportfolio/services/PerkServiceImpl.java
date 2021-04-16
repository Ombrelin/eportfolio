package com.arsene.eportfolio.services;

import com.arsene.eportfolio.exceptions.ResourceNotFoundException;
import com.arsene.eportfolio.model.data.PerkRepository;
import com.arsene.eportfolio.model.entities.Perk;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerkServiceImpl implements PerkService {

    private final PerkRepository perkRepository;

    public PerkServiceImpl(PerkRepository perkRepository) {
        this.perkRepository = perkRepository;
    }

    @Override
    public Perk get(String id) {
        return perkRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<Perk> getAll() {
        return perkRepository.findAll();
    }

    @Override
    public Perk create(Perk dto) {
        return perkRepository.save(dto);
    }

    @Override
    public Perk update(String id, Perk dto) {
        Perk p = perkRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        p.setName(dto.getName());
        p.setImage(dto.getImage());
        return perkRepository.save(p);
    }

    @Override
    public void delete(String id) {
        perkRepository.deleteById(id);
    }
}

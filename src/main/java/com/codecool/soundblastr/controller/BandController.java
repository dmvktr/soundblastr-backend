package com.codecool.soundblastr.controller;

import com.codecool.soundblastr.entity.*;
import com.codecool.soundblastr.repository.BandRepository;
import com.codecool.soundblastr.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/band")
public class BandController {

    private final BandRepository bandRepository;

    private final EventRepository eventRepository;

    @Autowired
    public BandController(BandRepository bandRepository, EventRepository eventRepository) {
        this.bandRepository = bandRepository;
        this.eventRepository = eventRepository;
    }

    @PostMapping("/new")
    @ResponseBody
    public Band addBand(@RequestBody BandRequest bandRequest) {
        Band bandToAdd = Band.builder()
                .imageUrl(bandRequest.getImageUrl())
                .name(bandRequest.getName())
                .description(bandRequest.getDescription())
                .genres(bandRequest.getGenres())
                .build();
        return bandRepository.save(bandToAdd);
    }

    @GetMapping("/{bandId}")
    public Band getBand(@PathVariable Long bandId) {
        return bandRepository.findById(bandId).orElse(null);
    }

    @GetMapping("/all")
    public List<Band> getAllBands() {
        return bandRepository.findAll();
    }

    @DeleteMapping("/{bandId}")
    public JsonMessage deleteBand(@PathVariable Long bandId) {

        if (eventRepository.findEventsByBandId(bandId).size() != 0) {
            return new JsonMessage(Status.NO_ACTION, "Band #" + bandId + " has associated events and it cannot be deleted.");
        }

        try {
            bandRepository.deleteById(bandId);
            return new JsonMessage(Status.OK, "Successfully deleted band #" + bandId + ".");
        } catch (EmptyResultDataAccessException e) {
            return new JsonMessage(Status.NO_ACTION, "Band #" + bandId + " not found, nothing happened");
        }
    }

    @PutMapping("/{bandId}")
    @ResponseBody
    public Object updateBand(@PathVariable Long bandId, @RequestBody BandRequest bandRequest) {
        Band bandToUpdate = bandRepository.findById(bandId).orElse(null);
        if (bandToUpdate == null) {
            return new JsonMessage(Status.NO_ACTION, "Band #" + bandId + " not found, nothing happened.");
        }

        bandToUpdate.setImageUrl(bandRequest.getImageUrl());
        bandToUpdate.setName(bandRequest.getName());
        bandToUpdate.setDescription(bandRequest.getDescription());
        bandToUpdate.setGenres(bandRequest.getGenres());

        return bandRepository.save(bandToUpdate);
    }
}

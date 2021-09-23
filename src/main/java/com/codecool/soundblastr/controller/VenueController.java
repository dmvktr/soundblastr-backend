package com.codecool.soundblastr.controller;

import com.codecool.soundblastr.entity.*;
import com.codecool.soundblastr.repository.EventRepository;
import com.codecool.soundblastr.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venue")
public class VenueController {

    private final VenueRepository venueRepository;

    private final EventRepository eventRepository;

    @Autowired
    public VenueController(VenueRepository venueRepository, EventRepository eventRepository) {
        this.venueRepository = venueRepository;
        this.eventRepository = eventRepository;
    }

    @PostMapping("/new")
    @ResponseBody
    public Venue addVenue(@RequestBody VenueRequest venueRequest) {
        Venue venueToAdd = Venue.builder()
                .imageUrl(venueRequest.getImageUrl())
                .name(venueRequest.getName())
                .description(venueRequest.getDescription())
                .address(venueRequest.getAddress())
                .capacity(venueRequest.getCapacity())
                .build();
        Venue newVenue = venueRepository.save(venueToAdd);
        newVenue.getAddress().setVenue(newVenue);
        return newVenue;
    }

    @GetMapping("/{venueId}")
    public Venue getVenue(@PathVariable Long venueId) {
        return venueRepository.findById(venueId).orElse(null);
    }

    @GetMapping("/all")
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    @DeleteMapping("/{venueId}")
    public JsonMessage deleteVenue(@PathVariable Long venueId) {

        if (eventRepository.findEventsByVenueId(venueId).size() != 0) {
            return new JsonMessage(Status.NO_ACTION, "Venue #" + venueId + " has associated events and it cannot be deleted.");
        }

        try {
            venueRepository.deleteById(venueId);
            return new JsonMessage(Status.OK, "Successfully deleted venue #" + venueId + ".");
        } catch (EmptyResultDataAccessException e) {
            return new JsonMessage(Status.NO_ACTION, "Venue #" + venueId + " not found, nothing happened");
        }
    }

    @PutMapping("/{venueId}")
    @ResponseBody
    public Object updateVenue(@PathVariable Long venueId, @RequestBody VenueRequest venueRequest) {
        Venue venueToUpdate = venueRepository.findById(venueId).orElse(null);

        if (venueToUpdate == null) {
            return new JsonMessage(Status.NO_ACTION, "Venue #" + venueId + " not found, nothing happened.");
        }

        venueToUpdate.setImageUrl(venueRequest.getImageUrl());
        venueToUpdate.setName(venueRequest.getName());
        venueToUpdate.setAddress(venueRequest.getAddress());
        venueToUpdate.setCapacity(venueRequest.getCapacity());
        venueToUpdate.setDescription(venueRequest.getDescription());

        Venue newVenue = venueRepository.save(venueToUpdate);
        newVenue.getAddress().setVenue(newVenue);
        return newVenue;
    }

}

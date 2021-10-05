package com.codecool.soundblastr.controller;

import com.codecool.soundblastr.controller.exception.IllegalOperationException;
import com.codecool.soundblastr.entity.*;
import com.codecool.soundblastr.repository.EventRepository;
import com.codecool.soundblastr.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Venue> addVenue(@RequestBody VenueRequest venueRequest) {
        Venue venueToAdd = Venue.builder()
            .imageUrl(venueRequest.getImageUrl())
            .name(venueRequest.getName())
            .description(venueRequest.getDescription())
            .address(venueRequest.getAddress())
            .capacity(venueRequest.getCapacity())
            .build();

        Venue vv = venueRepository.save(venueToAdd);
        return ResponseEntity.ok(vv);
    }

    @GetMapping("/{venueId}")
    public Venue getVenue(@PathVariable Long venueId) {
        return venueRepository.findById(venueId).orElseThrow(() ->
            new DataAccessResourceFailureException("Venue not found!"));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Venue>> getAllVenues() {
        return ResponseEntity.ok(venueRepository.findAll());
    }

    @DeleteMapping("/{venueId}")
    public ResponseEntity<Object> deleteVenue(@PathVariable Long venueId) {

        if (eventRepository.findEventsByVenueId(venueId).size() != 0) {
            throw new IllegalOperationException("Venue #" + venueId + " has associated events and it cannot be deleted.");
        }

        try {
            venueRepository.deleteById(venueId);
            return ResponseEntity.ok("Successfully deleted venue #" + venueId + ".");
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Venue #" + venueId + " not found!");
        }
    }

    @PutMapping("/{venueId}")
    @ResponseBody
    public ResponseEntity<Venue> updateVenue(@PathVariable Long venueId, @RequestBody VenueRequest venueRequest) {
        Venue venueToUpdate = venueRepository.findById(venueId).orElseThrow(() -> new DataAccessResourceFailureException(
            "Venue not found!"));

        venueToUpdate.setImageUrl(venueRequest.getImageUrl());
        venueToUpdate.setName(venueRequest.getName());
        venueToUpdate.setAddress(venueRequest.getAddress());
        venueToUpdate.setCapacity(venueRequest.getCapacity());
        venueToUpdate.setDescription(venueRequest.getDescription());

        Venue newVenue = venueRepository.save(venueToUpdate);

        return ResponseEntity.ok(newVenue);
    }

}

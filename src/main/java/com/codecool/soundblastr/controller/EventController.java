package com.codecool.soundblastr.controller;

import com.codecool.soundblastr.entity.*;
import com.codecool.soundblastr.repository.BandRepository;
import com.codecool.soundblastr.repository.EventRepository;
import com.codecool.soundblastr.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/event")
public class EventController {

    private final EventRepository eventRepository;
    private final BandRepository bandRepository;
    private final VenueRepository venueRepository;

    @Autowired
    public EventController(EventRepository eventRepository, BandRepository bandRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.bandRepository = bandRepository;
        this.venueRepository = venueRepository;
    }

    @PostMapping("/new")
    public ResponseEntity<Event> addEvent(@RequestBody EventRequest eventRequest) {
        Band band = bandRepository.findById(eventRequest.getBandId()).orElseThrow(
            () -> new DataAccessResourceFailureException("Band #" + eventRequest.getBandId() + " not found!"));
        Venue venue = venueRepository.findById(eventRequest.getVenueId()).orElseThrow(
            () -> new DataAccessResourceFailureException("Venue #" + eventRequest.getVenueId() + " not found!"));

        Event newEvent = Event.builder()
            .title(eventRequest.getTitle())
            .imageUrl(eventRequest.getImageUrl())
            .date(eventRequest.getDate())
            .ticketPrice(eventRequest.getTicketPrice())
            .description(eventRequest.getDescription())
            .band(band)
            .venue(venue)
            .build();
        return ResponseEntity.ok(eventRepository.save(newEvent));
    }

    @GetMapping("/{eventId}")
    public Event getEvent(@PathVariable Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
            new DataAccessResourceFailureException("The requested event was not found!"));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventRepository.findAll());
    }

    @GetMapping("/band/{bandId}")
    public ResponseEntity<List<Event>> getEventsForBand(@PathVariable Long bandId) {
        return ResponseEntity.ok(eventRepository.findEventsByBandId(bandId));
    }

    @GetMapping("/venue/{venueId}")
    public ResponseEntity<List<Event>> getEventsForVenue(@PathVariable Long venueId) {
        return ResponseEntity.ok(eventRepository.findEventsByVenueId(venueId));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Object> deleteEvent(@PathVariable Long eventId) {
        try {
            eventRepository.deleteById(eventId);
            return ResponseEntity.ok("Successfully deleted event #" + eventId + ".");
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Event #" + eventId + " not found!");
        }
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Object> updateEvent(@PathVariable Long eventId, @RequestBody EventRequest eventRequest) {
        Event eventToUpdate = eventRepository.findById(eventId).orElseThrow(
            () -> new DataAccessResourceFailureException("Event #" + eventId + " not found!"));
        Band band = bandRepository.findById(eventRequest.getBandId()).orElseThrow(
            () -> new DataAccessResourceFailureException("Band #" + eventRequest.getBandId() + " not found!"));
        Venue venue = venueRepository.findById(eventRequest.getVenueId()).orElseThrow(
            () -> new DataAccessResourceFailureException("Venue #" + eventRequest.getVenueId() + " not found!"));

        eventToUpdate.setTitle(eventRequest.getTitle());
        eventToUpdate.setImageUrl(eventRequest.getImageUrl());
        eventToUpdate.setDate(eventRequest.getDate());
        eventToUpdate.setTicketPrice(eventRequest.getTicketPrice());
        eventToUpdate.setDescription(eventRequest.getDescription());
        eventToUpdate.setBand(band);
        eventToUpdate.setVenue(venue);

        return ResponseEntity.ok(eventRepository.save(eventToUpdate));
    }

}

package com.codecool.soundblastr.controller;

import com.codecool.soundblastr.entity.*;
import com.codecool.soundblastr.repository.BandRepository;
import com.codecool.soundblastr.repository.EventRepository;
import com.codecool.soundblastr.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@CrossOrigin
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
    public Object addEvent(@RequestBody EventRequest eventRequest) {
        Band band;
        Venue venue;
        try {
            band = bandRepository.getById(eventRequest.getBandId());
            venue = venueRepository.getById(eventRequest.getVenueId());
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(
                "Band #" + eventRequest.getBandId() + " or Venue #" + eventRequest.getVenueId() + " not found");
        }

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
        return eventRepository.findById(eventId).orElse(null);
    }

    @GetMapping("/all")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @GetMapping("/band/{bandId}")
    public List<Event> getEventsForBand(@PathVariable Long bandId) {
        return eventRepository.findEventsByBandId(bandId);
    }

    @GetMapping("/venue/{venueId}")
    public List<Event> getEventsForVenue(@PathVariable Long venueId) {
        return eventRepository.findEventsByVenueId(venueId);
    }

    @DeleteMapping("/{eventId}")
    public JsonMessage deleteEvent(@PathVariable Long eventId) {
        try {
            eventRepository.deleteById(eventId);
            return new JsonMessage(Status.OK, "Successfully deleted event #" + eventId + ".");
        } catch (EmptyResultDataAccessException e) {
            return new JsonMessage(Status.NO_ACTION, "Event #" + eventId + " not found, nothing happened");
        }
    }

    @PutMapping("/{eventId}")
    public Object updateEvent(@PathVariable Long eventId, @RequestBody EventRequest eventRequest) {
        Band band;
        Venue venue;
        Event eventToUpdate;

        eventToUpdate = eventRepository.findById(eventId).orElse(null);
        if (eventToUpdate == null) {
            return new JsonMessage(Status.NO_ACTION, "Event #" + eventId + " not found, nothing happened.");
        }

        try {
            band = bandRepository.getById(eventRequest.getBandId());
            venue = venueRepository.getById(eventRequest.getVenueId());
        } catch (EntityNotFoundException e) {
            return new JsonMessage(Status.NO_ACTION, "Band #" + eventRequest.getBandId() + " or Venue #" + eventRequest.getVenueId() + " not found, nothing happened.");
        }

        eventToUpdate.setTitle(eventRequest.getTitle());
        eventToUpdate.setImageUrl(eventRequest.getImageUrl());
        eventToUpdate.setDate(eventRequest.getDate());
        eventToUpdate.setTicketPrice(eventRequest.getTicketPrice());
        eventToUpdate.setDescription(eventRequest.getDescription());
        eventToUpdate.setBand(band);
        eventToUpdate.setVenue(venue);
        return eventRepository.save(eventToUpdate);
    }

}

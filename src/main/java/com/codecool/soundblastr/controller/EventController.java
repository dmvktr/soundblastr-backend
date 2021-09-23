package com.codecool.soundblastr.controller;

import com.codecool.soundblastr.entity.*;
import com.codecool.soundblastr.repository.BandRepository;
import com.codecool.soundblastr.repository.EventRepository;
import com.codecool.soundblastr.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/event")
public class EventController {

    private EventRepository eventRepository;
    private BandRepository bandRepository;
    private VenueRepository venueRepository;

    @Autowired
    public EventController(EventRepository eventRepository, BandRepository bandRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.bandRepository = bandRepository;
        this.venueRepository = venueRepository;
    }

    @PostMapping("")
    public Event addEvent(@RequestBody EventRequest eventRequest) {
        Band band = bandRepository.getById(eventRequest.getBandId());
        Venue venue = venueRepository.getById(eventRequest.getVenueId());
        Event newEvent = Event.builder()
                .title(eventRequest.getTitle())
                .imageUrl(eventRequest.getImageUrl())
                .date(eventRequest.getDate())
                .ticketPrice(eventRequest.getTicketPrice())
                .band(band)
                .venue(venue)
                .build();
        return eventRepository.save(newEvent);
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

    @PutMapping("/{eventId}")
    public Event updateEvent(@PathVariable Long eventId, @RequestBody EventRequest eventRequest) {
        Band band = bandRepository.getById(eventRequest.getBandId());
        Venue venue = venueRepository.getById(eventRequest.getVenueId());
        Event eventToUpdate = eventRepository.getById(eventId);
        eventToUpdate.setTitle(eventRequest.getTitle());
        eventToUpdate.setImageUrl(eventRequest.getImageUrl());
        eventToUpdate.setDate(eventRequest.getDate());
        eventToUpdate.setTicketPrice(eventRequest.getTicketPrice());
        eventToUpdate.setBand(band);
        eventToUpdate.setVenue(venue);
        return eventRepository.save(eventToUpdate);
    }

    @DeleteMapping("/{eventId}")
    public JsonMessage deleteEvent(@PathVariable Long eventId) {
        eventRepository.deleteById(eventId);
        return new JsonMessage(Status.OK, "Successfully deleted event #" +  eventId + ".");
    }

}

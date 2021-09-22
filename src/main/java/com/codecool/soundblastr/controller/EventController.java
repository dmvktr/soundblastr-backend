package com.codecool.soundblastr.controller;

import com.codecool.soundblastr.entity.Band;
import com.codecool.soundblastr.entity.Event;
import com.codecool.soundblastr.entity.EventRequest;
import com.codecool.soundblastr.entity.Venue;
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
                .price(eventRequest.getPrice())
                .date(eventRequest.getDate())
                .band(band)
                .venue(venue)
                .build();
        return eventRepository.save(newEvent);
    }

    @GetMapping("/all")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @GetMapping("/{eventId}")
    public Event getEvent(@PathVariable Long eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }
}

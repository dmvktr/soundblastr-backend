package com.codecool.soundblastr.controller;

import com.codecool.soundblastr.entity.*;
import com.codecool.soundblastr.repository.EventRepository;
import com.codecool.soundblastr.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/venue")
public class VenueController {

    private final VenueRepository venueRepository;

    private final EventRepository eventRepository;

    @Autowired
    public VenueController(VenueRepository venueRepository,
                           EventRepository eventRepository) {
        this.venueRepository = venueRepository;
        this.eventRepository = eventRepository;
    }

    @PostMapping("/new")
    @ResponseBody
    public Venue addVenue(@RequestBody String name, Address address, int numberOfSeats) {
        Venue venueToAdd = Venue.builder()
                .name(name)
                .address(address)
                .numberOfSeats(numberOfSeats)
                .build();
        return venueRepository.save(venueToAdd);
    }

    @GetMapping("/id={venueId}")
    public Venue getVenue(@PathVariable("venueId") String venueId) {
        return venueRepository.findById(Long.parseLong(venueId)).orElse(null);
    }

    @GetMapping("/all")
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    @DeleteMapping("/id={venueId}")
    public void deleteVenue(@PathVariable("venueId") String venueId) {
        venueRepository.deleteById(Long.parseLong(venueId));
    }

    // TODO - move this to EventController
//    @GetMapping("/events/id={venueId}")
//    public List<Event> getEventsForVenue(@PathVariable("venueId") String venueId) {
//        return eventRepository.findEventsByVenueId(Long.parseLong(venueId));
//    }

    @PutMapping("/id={venueId}")
    @ResponseBody
    public Venue updateVenue(@PathVariable("venueId") String venueId, @RequestBody String name, Address address, int numberOfSeats) {
        Venue venueToUpdate = venueRepository.getVenueById(Long.parseLong(venueId));
        venueToUpdate.setName(name);
        venueToUpdate.setAddress(address);
        venueToUpdate.setNumberOfSeats(numberOfSeats);
        return venueRepository.save(venueToUpdate);
    }

}

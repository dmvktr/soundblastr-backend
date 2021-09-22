package com.codecool.soundblastr.controller;

import com.codecool.soundblastr.entity.Band;
import com.codecool.soundblastr.entity.Event;
import com.codecool.soundblastr.entity.Genre;
import com.codecool.soundblastr.repository.BandRepository;
import com.codecool.soundblastr.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Band addBand(@RequestBody String name, List<Genre> genreList) {
        Band bandToAdd = Band.builder().name(name).genres(genreList).build();
        return bandRepository.save(bandToAdd);
    }

    @GetMapping("/id={bandId}")
    public Band getBand(@PathVariable("bandId") String bandId) {
        return bandRepository.findById(Long.parseLong(bandId)).orElse(null);
    }

    @GetMapping("/all")
    public List<Band> getAllBands() {
        return bandRepository.findAll();
    }

    @DeleteMapping("/id={bandId}")
    public void deleteBand(@PathVariable("bandId") String bandId) {
        bandRepository.deleteById(Long.parseLong(bandId));
    }

    @GetMapping("/events/id={bandId}")
    public List<Event> getEventsForBand(@PathVariable("bandId") String bandId) {
        return eventRepository.findEventsByBandId(Long.parseLong(bandId));
    }

    @PutMapping("/id={bandId}")
    @ResponseBody
    public Band updateBand(@PathVariable("bandId") String bandId, @RequestBody String name, Set<Genre> genres) {
        Band bandToUpdate = bandRepository.getBandById(Long.parseLong(bandId));
        bandToUpdate.setName(name);
        bandToUpdate.setGenres(genres);
        return bandRepository.save(bandToUpdate);
    }
}

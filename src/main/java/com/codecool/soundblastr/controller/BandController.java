package com.codecool.soundblastr.controller;

import com.codecool.soundblastr.controller.exception.IllegalOperationException;
import com.codecool.soundblastr.entity.*;
import com.codecool.soundblastr.repository.BandRepository;
import com.codecool.soundblastr.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
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
    public ResponseEntity<Band> addBand(@RequestBody BandRequest bandRequest) {
        Band newBand = Band.builder()
            .imageUrl(bandRequest.getImageUrl())
            .name(bandRequest.getName())
            .description(bandRequest.getDescription())
            .genres(bandRequest.getGenres())
            .build();
        return ResponseEntity.ok(bandRepository.save(newBand));
    }

    @GetMapping("/{bandId}")
    public ResponseEntity<BandResponse> getBand(@PathVariable Long bandId) {
         Band band = bandRepository.findById(bandId).orElseThrow(
            () -> new DataAccessResourceFailureException("Band not found!"));
        Map<String, Set<Genre>> selection = Map.of("selected", band.getGenres(), "all", Set.of(Genre.values()));
        BandResponse bandResponseObj = BandResponse.builder()
            .id(band.getId())
            .name(band.getName())
            .imageUrl(band.getImageUrl())
            .description(band.getDescription())
            .genreSelection(selection)
            .build();
        return ResponseEntity.ok(bandResponseObj);
    }

    @GetMapping("/getGenres")
    public ResponseEntity<Genre[]> getBand() {
        return ResponseEntity.ok(Genre.values());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Band>> getAllBands() {
        List<Band> bands = bandRepository.findAll();
        return ResponseEntity.ok(bands);
    }

    @DeleteMapping("/{bandId}")
    public ResponseEntity<Object> deleteBand(@PathVariable Long bandId) {

        if (eventRepository.findEventsByBandId(bandId).size() != 0) {
            throw new IllegalOperationException("Band #" + bandId + " has associated events and it cannot be deleted.");
        }

        try {
            bandRepository.deleteById(bandId);
            return ResponseEntity.ok("Successfully deleted band #" + bandId + ".");
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Band #" + bandId + " not found!");
        }
    }

    @PutMapping("/{bandId}")
    @ResponseBody
    public ResponseEntity<Band> updateBand(@PathVariable Long bandId, @RequestBody BandRequest bandRequest) {
        Band bandToUpdate = bandRepository.findById(bandId).orElseThrow(() -> new DataAccessResourceFailureException(
            "Band not found!"));

        bandToUpdate.setImageUrl(bandRequest.getImageUrl());
        bandToUpdate.setName(bandRequest.getName());
        bandToUpdate.setDescription(bandRequest.getDescription());
        bandToUpdate.setGenres(bandRequest.getGenres());

        return ResponseEntity.ok(bandRepository.save(bandToUpdate));
    }
}

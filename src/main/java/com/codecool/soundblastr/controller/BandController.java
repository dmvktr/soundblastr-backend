package com.codecool.soundblastr.controller;

import com.codecool.soundblastr.entity.Band;
import com.codecool.soundblastr.entity.Genre;
import com.codecool.soundblastr.repository.BandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/band")
public class BandController {

    private final BandRepository bandRepository;

    @Autowired
    public BandController(BandRepository bandRepository) {
        this.bandRepository = bandRepository;
    }

    @PostMapping("/new")
    @ResponseBody
    public Band addBand(@RequestBody String name, List<Genre> genreList) {
        Band bandToAdd = Band.builder().name(name).genres(genreList).build();
        return bandRepository.save(bandToAdd);
    }

    @GetMapping("/{bandId}")
    public Band getBand(@PathVariable("bandId") String bandId) {
        return bandRepository.findById(Long.parseLong(bandId)).orElse(null);
    }

    @GetMapping("/all")
    public List<Band> getAllBands() {
        return bandRepository.findAll();
    }

    @DeleteMapping("/{bandId}")
    public void deleteBand(@PathVariable("bandId") String bandId) {
        bandRepository.deleteById(Long.parseLong(bandId));
    }

    @PutMapping("/{bandId}")
    @ResponseBody
    public Band updateBand(@PathVariable Long bandId, @RequestBody String name, Set<Genre> genres) {
        Band bandToUpdate = bandRepository.getById(bandId);
        bandToUpdate.setName(name);
        bandToUpdate.setGenres(genres);
        return bandRepository.save(bandToUpdate);
    }
}

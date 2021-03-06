package com.codecool.soundblastr.repository;

import com.codecool.soundblastr.entity.Band;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class AllRepositoryTest {

    @Autowired
    BandRepository bandRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    VenueRepository venueRepository;

    @Test
    public void bandRepository_bandIsPersisted_whenBandAddedWithValidDetails() {
        Band testBand = Band.builder().name("Test").build();
        bandRepository.save(testBand);

        List<Band> bandList = bandRepository.findAll();

        assertThat(bandList).hasSize(1).anyMatch(band -> band.getName().equals("Test"));
    }

    @Test
    public void bandRepository_exceptionThrown_whenBandAddedWithoutRequiredFields() {
        Band testBand = Band.builder().build();

        assertThrows(DataIntegrityViolationException.class, () -> {
            bandRepository.save(testBand);
        });
    }

}
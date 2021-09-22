package com.codecool.soundblastr.repository;

import com.codecool.soundblastr.entity.Band;
import com.codecool.soundblastr.entity.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class AllRepositoryTest {

    @Autowired
    private BandRepository bandRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private TestEntityManager entityManager;

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

    @Test
    public void eventRepositoryDelete_eventIsDeletedFromBandCollection_whenEventIsDeleted(){
        Event event = Event.builder().
                title("test concert").
                date(LocalDate.of(2021,12,12))
                .build();
        Band band = Band.builder()
                .name("test")
                .event(event)
                .build();
        event.setBand(band);
        bandRepository.save(band);
        eventRepository.save(event);
        List<Event> eventsAll = eventRepository.findAll();
        List<Band> bandsAll = bandRepository.findAll();
        assertEquals(1, eventsAll.size());
        assertEquals(1, bandsAll.size());

        List<Band> bands = bandRepository.findAllByEventsContains(event);
        System.out.println(bands.size());

        //delete event form bands first
        for (Band bandToUpdate : bands) {
            System.out.println("egy band updated elvileg");
            List<Event> bandEvents = bandToUpdate.getEvents();
            band.setEvents(bandEvents.stream().filter(eventToFilter -> !eventToFilter.equals(event)).collect(Collectors.toList()));
        }
        eventRepository.delete(event);

        List<Event> events = eventRepository.findAll();
        int expected = 0;
        assertEquals(expected, events.size());
        bands = bandRepository.findAll();
        assertEquals(1, bands.size());
    }

}
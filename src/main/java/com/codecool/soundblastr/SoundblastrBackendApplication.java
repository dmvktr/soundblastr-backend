package com.codecool.soundblastr;

import com.codecool.soundblastr.entity.Band;
import com.codecool.soundblastr.entity.Event;
import com.codecool.soundblastr.entity.Genre;
import com.codecool.soundblastr.entity.Venue;
import com.codecool.soundblastr.repository.BandRepository;
import com.codecool.soundblastr.repository.EventRepository;
import com.codecool.soundblastr.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class SoundblastrBackendApplication {

    @Autowired
    private BandRepository bandRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private EventRepository eventRepository;

    public static void main(String[] args) {
        SpringApplication.run(SoundblastrBackendApplication.class, args);
    }

    @Bean
    @Profile("prod")
    public CommandLineRunner init() {
        return args -> {
            Band muse = Band.builder()
                    .name("Muse")
                    .genre(Genre.INDIE)
                    .genre(Genre.POP)
                    .build();

            Band killers = Band.builder()
                    .name("Killers")
                    .genre(Genre.INDIE)
                    .build();
            bandRepository.save(muse);
            bandRepository.save(killers);

            Venue budapestPark = Venue.builder()
                    .name("Budapest Park")
                    .build();

            venueRepository.save(budapestPark);

            Band museFromDatabase = bandRepository.findById(100L).get();
            Band killersFromDatabase = bandRepository.findById(101L).get();
            Venue budapestParkFromDatabase = venueRepository.findById(1000L).get();

            Event museConcert = Event.builder()
                    .title("Muse Concert")
                    .date(LocalDate.of(2021, 9, 22))
                    .band(museFromDatabase)
                    .venue(budapestParkFromDatabase).build();

            Event killersConcert = Event.builder()
                    .title("Killers Concert")
                    .date(LocalDate.of(2021, 9, 22))
                    .band(killers)
                    .venue(budapestParkFromDatabase).build();

            eventRepository.save(museConcert);
            eventRepository.saveAndFlush(killersConcert);
            Event museConcertFromDatabase = eventRepository.findById(1L).get();
            Event killersConcertFromDatabase = eventRepository.findById(2L).get();

//            budapestParkFromDatabase.addEvent(museConcertFromDatabase);
//            museFromDatabase.addEvent(museConcertFromDatabase);
//            budapestParkFromDatabase.addEvent(killersConcertFromDatabase);
//            killersFromDatabase.addEvent(killersConcertFromDatabase);
            bandRepository.save(museFromDatabase);
            bandRepository.save(killersFromDatabase);
            List<Band> bands = bandRepository.findAll();
        };
    }

}

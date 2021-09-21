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

            Band museFromMemory = bandRepository.findById(100L).get();
            Venue budapestParkFromMemory = venueRepository.findById(1000L).get();

            Event museConcert = Event.builder()
                    .title("Muse Concert")
                    .date(LocalDate.of(2021, 9, 22))
                    .band(museFromMemory)
                    .venue(budapestParkFromMemory).build();

            eventRepository.saveAndFlush(museConcert);
            Event museConcertFromDatabase = eventRepository.findById(1L).get();

//            budapestParkFromMemory.addEvent(museConcertFromDatabase);
//            museFromMemory.addEvent(museConcertFromDatabase);

        };
    }

}

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
                    .imageUrl("https://cdn.freelogovectors.net/wp-content/uploads/2019/10/muse-logo.png")
                    .description("Muse are an English rock band from Teignmouth, Devon, formed in 1994.")
                    .build();

            bandRepository.save(muse);

            Band killers = Band.builder()
                    .name("Killers")
                    .genre(Genre.INDIE)
                    .imageUrl("https://logoeps.com/wp-content/uploads/2013/06/the-killers-vector-logo.png")
                    .description("The Killers are an American rock band formed in Las Vegas in 2001 by Brandon Flowers.")
                    .build();

            bandRepository.save(killers);

            Venue budapestPark = Venue.builder()
                    .name("Budapest Park")
                    .imageUrl("https://9.kerulet.ittlakunk.hu/files/ittlakunk/styles/large/public/upload/company/1256/budapest_park_logo.png?itok=6iDPG-pX")
                    .description("Budapest Park is Europe's Largest Outdoor Concert and Entertainment Venue")
                    .build();

            venueRepository.save(budapestPark);

            Band museDB = bandRepository.findById(100L).get();
            Band killersDB = bandRepository.findById(101L).get();
            Venue budapestParkDB = venueRepository.findById(1000L).get();

            Event museConcert = Event.builder()
                    .title("Muse Concert")
                    .date(LocalDate.of(2021, 9, 22))
                    .band(museDB)
                    .imageUrl("https://seeklogo.com/images/M/muse-knights-of-cydonia-logo-9172377960-seeklogo.com.png")
                    .description("Enjoy Muse at Budapest Park!")
                    .venue(budapestParkDB).build();

            Event killersConcert = Event.builder()
                    .title("Killers Concert")
                    .date(LocalDate.of(2021, 9, 22))
                    .band(killersDB)
                    .imageUrl("https://upload.wikimedia.org/wikipedia/en/thumb/1/17/The_Killers_-_Hot_Fuss.png/220px-The_Killers_-_Hot_Fuss.png")
                    .description("Enjoy Killers at Budapest Park!")
                    .venue(budapestParkDB).build();

            eventRepository.save(museConcert);
            eventRepository.save(killersConcert);

        };
    }

}

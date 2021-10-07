package com.codecool.soundblastr;

import com.codecool.soundblastr.entity.*;
import com.codecool.soundblastr.repository.BandRepository;
import com.codecool.soundblastr.repository.EventRepository;
import com.codecool.soundblastr.repository.UserRepository;
import com.codecool.soundblastr.repository.VenueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@Profile("prod")
public class DataInitializer implements CommandLineRunner {

    private final BandRepository bandRepository;

    private final VenueRepository venueRepository;

    private final EventRepository eventRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public DataInitializer(BandRepository bandRepository, VenueRepository venueRepository,
                           EventRepository eventRepository, UserRepository userRepository) {
        this.bandRepository = bandRepository;
        this.venueRepository = venueRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Override
    public void run(String... args) {
        log.debug("initializing sample data...");

        userRepository.save(
            AppUser.builder()
            .username("administrator")
            .password(passwordEncoder.encode("2"))
            .roles(List.of("ROLE_EMPLOYEE"))
            .build());

        userRepository.save(
            AppUser.builder()
                .username("manager")
                .password(passwordEncoder.encode("1"))
                .roles(List.of("ROLE_MANAGER"))
                .build());

        Band muse = Band.builder()
            .name("Muse")
            .genre(Genre.INDIE)
            .genre(Genre.POP)
            .imageUrl("https://i.pinimg.com/originals/fd/24/35/fd2435bd31a2b62206128f78c332d4b9.jpg")
            .description("Muse are an English rock band from Teignmouth, Devon, formed in 1994.")
            .build();

        Band killers = Band.builder()
            .name("Killers")
            .genre(Genre.INDIE)
            .imageUrl("https://d1yjjnpx0p53s8.cloudfront.net/styles/logo-thumbnail/s3/0021/3211/brand.gif?itok=JyGTQozV")
            .description("The Killers are an American rock band formed in Las Vegas in 2001 by Brandon Flowers.")
            .build();

        Band aws = Band.builder()
            .name("AWS")
            .genres(Arrays.asList(Genre.ROCK, Genre.METALCORE))
            .imageUrl("https://i.scdn.co/image/9cc26319c9d56ec61ed3aca950bd17043b698f72")
            .description("AWS (pronounced ah-vi-esh) is a Hungarian metalcore band formed in 2006 by Bence Brucker...")
            .build();

        Band punnany = Band.builder()
            .name("Punnany Massif")
            .genre(Genre.HIPHOP)
            .imageUrl("https://cdn.nwmgroups.hu/s/img/i/1207/20120702-punnany-massif-punnany-mate-hiphop-zenekar4.jpg")
            .description("Punnany Massif was founded in 2003 as a Hungarian hiphop band. Their genre is self-defined as social-funk.")
            .build();

        Band brains = Band.builder()
            .name("Brains")
            .genre(Genre.ELECTRONIC)
            .imageUrl("https://a1-images.myspacecdn.com/images03/22/be7b0621b98343219d992f09486503b8/600x600.jpg")
            .description("Brains is the most successful live band in the genre of electronic music in Hungary.")
            .build();

        Band eltonJohn = Band.builder()
            .name("Elton John")
            .genre(Genre.PIANIST)
            .genre(Genre.COMPOSER)
            .imageUrl("https://logovector.net/wp-content/uploads/2010/04/317594-elton-john-logo.png")
            .description("Sir Elton Hercules John CH CBE is an English singer, songwriter, pianist and composer.")
            .build();

        Address budapestParkAddress = Address.builder()
            .city("Budapest")
            .houseNr(60)
            .street("Soroksári út")
            .zipcode(1095)
            .build();

        Venue budapestPark = Venue.builder()
            .name("Budapest Park")
            .imageUrl("https://cseppek.hu/files/images/teljes_kepernyo_rogzitese_2015.04.26._133428.jpg")
            .description("Budapest Park is Europe's Largest Outdoor Concert and Entertainment Venue")
            .address(budapestParkAddress)
            .capacity(1000)
            .build();

        Address szigetAddress = Address.builder()
            .city("Budapest")
            .houseNr(1)
            .street("Hajógyári Island")
            .zipcode(1033)
            .build();

        Venue sziget = Venue.builder()
            .name("Sziget Festival")
            .imageUrl("https://blagussdmc.com/wp-content/uploads/08_Sziget_Festival.jpg")
            .description("The Sziget Festival in Budapest is one of the largest music and cultural festivals in Europe.")
            .address(szigetAddress)
            .capacity(576351)
            .build();

        Address barbaAddress = Address.builder()
            .city("Budapest")
            .houseNr(4)
            .street("Prielle Kornélia u.")
            .zipcode(1117)
            .build();

        Venue barbaNegra = Venue.builder()
            .name("Barba Negra")
            .imageUrl("https://yt3.ggpht.com/ytc/AKedOLQbQsrxFu6qklPtI9M3AaiibTubysXj3qgiFWDS=s900-c-k-c0x00ffffff-no-rj")
            .description("Barba Negra Music Club - An indoor concert hall with a unique atmosphere.")
            .address(barbaAddress)
            .capacity(700)
            .build();

        bandRepository.save(muse);
        bandRepository.save(killers);
        bandRepository.save(aws);
        bandRepository.save(punnany);
        bandRepository.save(brains);
        bandRepository.saveAndFlush(eltonJohn);

        venueRepository.save(budapestPark);
        venueRepository.save(sziget);
        venueRepository.save(barbaNegra);

        List<Band> bands = bandRepository.findAll();

        Band museDB = bandRepository.findById(101L).get();
        Band killersDB = bandRepository.findById(102L).get();
        Band punnanyDB = bandRepository.findById(104L).get();
        Band brainsDB = bandRepository.findById(105L).get();
        Band eltonJohnDB = bandRepository.findById(106L).get();

        Venue budapestParkDB = venueRepository.findById(1001L).get();
        Venue szigetDB = venueRepository.findById(1002L).get();
        Venue barbaNegraDB = venueRepository.findById(1003L).get();

        Event museConcert = Event.builder()
            .title("Muse Concert")
            .date(LocalDate.of(2021, 10, 1))
            .band(museDB)
            .imageUrl("https://seeklogo.com/images/M/muse-knights-of-cydonia-logo-9172377960-seeklogo.com.png")
            .description("Enjoy Muse at Sziget Festival")
            .ticketPrice(15000)
            .venue(szigetDB).build();

        Event killersConcert = Event.builder()
            .title("Killers Concert")
            .date(LocalDate.of(2022, 10, 22))
            .band(killersDB)
            .imageUrl("https://upload.wikimedia.org/wikipedia/en/thumb/1/17/The_Killers_-_Hot_Fuss.png/220px-The_Killers_-_Hot_Fuss.png")
            .description("Killers x Sziget - how awesome!")
            .ticketPrice(17000)
            .venue(szigetDB).build();

        Event punnanyBudapestPark = Event.builder()
            .title("Punnany Massif Concert")
            .date(LocalDate.of(2021, 10, 21))
            .band(punnanyDB)
            .imageUrl("https://assets.vimity.com/vi-eyJ0IjpbeyJyZXNpemUi/OnsibCI6IjcwMCIsImgi/OiI0NjcifX1dLCJpZCI6/ImkyNTQxNjIiLCJmIjoi/anBnIn0;.jpg")
            .description("This is it! Punnany Massif at Budapest Park")
            .ticketPrice(7000)
            .venue(budapestParkDB).build();


        Event punnanySziget = Event.builder()
            .title("Sziget with Punnany Massif")
            .date(LocalDate.of(2022, 8, 21))
            .band(punnanyDB)
            .imageUrl("https://www.a38.hu/storage/app/uploads/public/5ad/d96/3c8/thumb_101229_1200_0_0_0_auto.jpg")
            .description("Punnany Massif + Sziget, what you'll do this summer")
            .ticketPrice(12000)
            .venue(szigetDB).build();

        Event eltonJohnBarba = Event.builder()
            .title("Xmas with Elton @ Barba VIP")
            .date(LocalDate.of(2022, 10, 25))
            .band(eltonJohnDB)
            .imageUrl("https://i.dailymail.co.uk/1s/2020/12/25/14/37257044-9087565-Festive_wishes_Sir_Elton_John_stepped_into_Christmas_in_a_velvet-a-33_1608906091918.jpg")
            .description("Elton, Eggnog, Budapest & Wrapping Paper")
            .ticketPrice(119000)
            .venue(barbaNegraDB).build();

        Event brainsBarba = Event.builder()
            .title("Barba Negra meets Brains")
            .date(LocalDate.of(2021, 11, 1))
            .band(brainsDB)
            .imageUrl("https://rockabillyrules.com/assets/flyers/orig/the-brains-hamburg.jpg")
            .description("Brains and Barba negra are your ultimate November 1st programme")
            .ticketPrice(8900)
            .venue(barbaNegraDB).build();

        eventRepository.save(museConcert);
        eventRepository.save(killersConcert);
        eventRepository.save(punnanyBudapestPark);
        eventRepository.save(punnanySziget);
        eventRepository.save(eltonJohnBarba);
        eventRepository.save(brainsBarba);
    }
}

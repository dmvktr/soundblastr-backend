package com.codecool.soundblastr;

import com.codecool.soundblastr.entity.Band;
import com.codecool.soundblastr.entity.Genre;
import com.codecool.soundblastr.repository.BandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class SoundblastrBackendApplication {

    @Autowired
    private BandRepository bandRepository;

    public static void main(String[] args) {
        SpringApplication.run(SoundblastrBackendApplication.class, args);
    }

    @Bean
    @Profile("prod")
    public CommandLineRunner init() {
        return args -> {
            final Band muse = Band.builder()
                    .name("Muse")
                    .genre(Genre.INDIE)
                    .genre(Genre.POP)
                    .build();

            bandRepository.save(muse);

        };
    }

}

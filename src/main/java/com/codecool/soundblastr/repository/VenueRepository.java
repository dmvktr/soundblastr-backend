package com.codecool.soundblastr.repository;

import com.codecool.soundblastr.entity.Band;
import com.codecool.soundblastr.entity.Event;
import com.codecool.soundblastr.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, Long> {
    Venue getVenueById(long venueId);
}

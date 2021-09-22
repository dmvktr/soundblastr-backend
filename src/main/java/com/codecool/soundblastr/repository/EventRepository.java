package com.codecool.soundblastr.repository;

import com.codecool.soundblastr.entity.Band;
import com.codecool.soundblastr.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findEventsByVenueId(long venueId);
}

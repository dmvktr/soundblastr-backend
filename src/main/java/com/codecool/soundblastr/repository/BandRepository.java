package com.codecool.soundblastr.repository;

import com.codecool.soundblastr.entity.Band;
import com.codecool.soundblastr.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BandRepository extends JpaRepository<Band, Long> {

    public List<Band> findAllByEventsContains(Event event);
}

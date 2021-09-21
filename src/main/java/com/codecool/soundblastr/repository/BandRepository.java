package com.codecool.soundblastr.repository;

import com.codecool.soundblastr.entity.Band;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BandRepository extends JpaRepository<Band, Long> {
}

package com.codecool.soundblastr.repository;

import com.codecool.soundblastr.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}

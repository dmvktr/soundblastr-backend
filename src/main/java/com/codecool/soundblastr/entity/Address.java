package com.codecool.soundblastr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @SequenceGenerator(
            name = "address_sequence",
            sequenceName = "address_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "address_sequence"
    )
    @Column(
            name="id",
            updatable = false
    )
    @JsonIgnore
    private Long id;
    @Column(
            name="street",
            nullable = false
    )
    private String street;
    @Column(
            name="houseNr",
            nullable = false
    )
    private int houseNr;
    @Column(
            name="city",
            nullable = false
    )
    private String city;
    @Column(
            name="zipcode",
            nullable = false
    )
    private int zipcode;

    @JsonIgnore
    @OneToOne(mappedBy = "address")
    private Venue venue;
}
package com.codecool.soundblastr.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
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
    private String houseNr;
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

    @OneToOne
    private Venue venue;
}

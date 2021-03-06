package com.codecool.soundblastr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Event {

    @Id
    @SequenceGenerator(
            name = "event_sequence",
            sequenceName = "event_sequence",
            allocationSize = 1,
            initialValue = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "event_sequence"
    )
    @Column(
            name="id",
            updatable = false
    )
    private Long id;

    @Column(name="imageUrl")
    private String imageUrl;

    @Column(
            name="title",
            unique = true,
            nullable = false
    )
    private String title;

    @Column(
            name="date",
            nullable = false
    )
    private LocalDate date;

    @Column(name="ticketPrice")
    private Integer ticketPrice;

    @Column(name="description")
    private String description;

    @ManyToOne
    private Band band;

    @ManyToOne
    private Venue venue;
}

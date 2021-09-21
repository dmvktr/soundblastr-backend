package com.codecool.soundblastr.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venue {

    @Id
    @SequenceGenerator(
            name = "venue_sequence",
            sequenceName = "venue_sequence",
            allocationSize = 1,
            initialValue = 1000
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "venue_sequence"
    )
    @Column(
            name="id",
            updatable = false
    )
    private Long id;

    @Column(
            name="name",
            unique = true,
            nullable = false
    )
    private String name;

    private int numberOfSeats;

    @Singular
    @OneToMany(mappedBy = "venue", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    List<Event> events;

    public void addEvent(Event event) {
        if (events == null) {
            events = new ArrayList<>();
        }
        events.add(event);
    }

}

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
public class Band {

    @Id
    @SequenceGenerator(
            name = "band_sequence",
            sequenceName = "band_sequence",
            allocationSize = 1,
            initialValue = 100
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "band_sequence"
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

    @ElementCollection
    @Singular
    @Enumerated(EnumType.STRING)
    private Set<Genre> genres;

    @Singular
    @OneToMany(mappedBy = "band", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    List<Event> events;

    public void addEvent(Event event) {
        if (events == null) {
            events = new ArrayList<>();
        }
        events.add(event);
    }


}

package com.codecool.soundblastr.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
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

    @JsonIgnore
    @Singular
    @OneToMany(mappedBy = "band",fetch = FetchType.EAGER)
    List<Event> events = new ArrayList<>();

    public void addEvent(Event event) {
        if (events == null) {
            events = new ArrayList<>();
        }
        this.events.add(event);
        if(event.getBand() != this)     event.setBand(this);
    }


}

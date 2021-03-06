package com.codecool.soundblastr.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Venue {

    @Id
    @SequenceGenerator(
            name = "venue_sequence",
            sequenceName = "venue_sequence",
            allocationSize = 1,
            initialValue = 1001
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

    @Column(name="imageUrl")
    private String imageUrl;

    @Column(
            name="name",
            unique = true,
            nullable = false
    )
    private String name;

    @Column(name="description")
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Column(name="capacity")
    private Integer capacity;

}

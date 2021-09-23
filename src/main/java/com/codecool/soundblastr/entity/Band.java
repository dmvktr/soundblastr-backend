package com.codecool.soundblastr.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Band {

    @Id
    @SequenceGenerator(
            name = "band_sequence",
            sequenceName = "band_sequence",
            allocationSize = 1,
            initialValue = 101
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

    @ElementCollection
    @Singular
    @Enumerated(EnumType.STRING)
    private Set<Genre> genres;
}

package com.codecool.soundblastr.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Band {

    @Id
    @GeneratedValue
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

}

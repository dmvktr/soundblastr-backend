package com.codecool.soundblastr.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BandRequest {

    private String imageUrl;
    private String name;
    private String description;
    private Set<Genre> genres;

}

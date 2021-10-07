package com.codecool.soundblastr.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BandResponse {
    private Long id;
    private String imageUrl;
    private String name;
    private String description;
    private Map<String, Set<Genre>> genreSelection;
}

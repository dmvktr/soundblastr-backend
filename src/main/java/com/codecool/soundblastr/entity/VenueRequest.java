package com.codecool.soundblastr.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenueRequest {

    private String imageUrl;
    private String name;
    private String description;
    private Address address;
    private Integer capacity;

}

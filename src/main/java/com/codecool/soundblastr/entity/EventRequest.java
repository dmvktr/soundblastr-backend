package com.codecool.soundblastr.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {

    private String title;
    private LocalDate date;
    private int ticketPrice;
    private String imageUrl;
    private Long bandId;
    private Long venueId;

}

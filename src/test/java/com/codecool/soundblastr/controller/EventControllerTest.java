package com.codecool.soundblastr.controller;

import com.codecool.soundblastr.entity.Band;
import com.codecool.soundblastr.entity.Event;
import com.codecool.soundblastr.entity.Venue;
import com.codecool.soundblastr.repository.BandRepository;
import com.codecool.soundblastr.repository.EventRepository;
import com.codecool.soundblastr.repository.VenueRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EventRepository mockEventRepository;


    @Test
    void getAllEventsRoute_shouldReturnJsonWithListOfEvents_whenMultipleEventsArePresent() throws Exception {
        Event event = Event.builder().venue(
            Venue.builder().name("Budapest Park").build())
            .date(LocalDate.of(2021,12, 12)).title("rock concert").build();
        Event event2 = Event.builder().date(LocalDate.of(2021,12,12)).title("pop concert").build();
        Event event3 = Event.builder().date(LocalDate.of(2021,12,12)).title("electronic concert").build();
        mockEventRepository.saveAll(List.of(event, event2, event3));

        Mockito.when(mockEventRepository.findAll()).thenReturn(List.of(event, event2, event3));

        mockMvc.perform(MockMvcRequestBuilders
            .get("/event/all"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value("rock concert"))
            .andExpect(jsonPath("$[0].venue.name").value("Budapest Park"))
            .andExpect(jsonPath("$[1].title").value("pop concert"))
            .andExpect(jsonPath("$[2].title").value("electronic concert"));
    }

    @Test
    void addEventRoute_shouldReturnJsonWithCreatedEvent_whenPostRequestIsSuccessful() throws Exception {
        Event event = Event.builder().id(1L).title("rock concert").date(LocalDate.of(2021,12, 12))
            .venue(Venue.builder().id(1000L).name("Budapest Park").build())
            .band(Band.builder().id(100L).name("Muse").build())
            .build();
        Mockito.when(mockEventRepository.save(any())).thenReturn(event);
        mockMvc.perform(MockMvcRequestBuilders.post("/event/new")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("{\"title\": \"Killers new Concert from Postman 3\", \"date\": \"2031-01-01\", \"price\": 400, " +
                "\"bandId\": 101, \"venueId\": 1000}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("rock concert"))
            .andExpect(jsonPath("$.band.id").value( 100))
            .andExpect(jsonPath("$.venue.id").isNotEmpty())
            .andExpect(jsonPath("$.venue.name").value("Budapest Park"))
            .andExpect(jsonPath("$.band.name").value("Muse"));
    }
}
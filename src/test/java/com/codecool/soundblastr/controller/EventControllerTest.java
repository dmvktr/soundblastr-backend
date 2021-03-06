package com.codecool.soundblastr.controller;

import com.codecool.soundblastr.entity.Band;
import com.codecool.soundblastr.entity.Event;
import com.codecool.soundblastr.entity.Venue;
import com.codecool.soundblastr.repository.BandRepository;
import com.codecool.soundblastr.repository.EventRepository;
import com.codecool.soundblastr.repository.VenueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EventControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EventRepository mockEventRepository;

    @MockBean
    BandRepository bandRepository;

    @MockBean
    VenueRepository venueRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @WithMockUser("spring")
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
            .get("/event/all").contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value("rock concert"))
            .andExpect(jsonPath("$[0].venue.name").value("Budapest Park"))
            .andExpect(jsonPath("$[1].title").value("pop concert"))
            .andExpect(jsonPath("$[2].title").value("electronic concert"));
    }

    @WithMockUser("spring")
    @Test
    void addEventRoute_shouldReturnJsonWithCreatedEvent_whenPostRequestIsSuccessful() throws Exception {
        Venue venue = Venue.builder().id(1000L).name("Budapest Park").build();
        Band band = Band.builder().id(100L).name("Muse").build();
        Event event = Event.builder().id(1L).title("rock concert").date(LocalDate.of(2021,12, 12))
            .venue(venue)
            .band(band)
            .build();

        Mockito.when(bandRepository.findById(any())).thenReturn(Optional.ofNullable(band));
        Mockito.when(venueRepository.findById(any())).thenReturn(Optional.ofNullable(venue));
        Mockito.when(mockEventRepository.save(any())).thenReturn(event);

        mockMvc.perform(MockMvcRequestBuilders.post("/event/new")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("{\"title\": \"Killers new Concert from Postman 3\", \"date\": \"2031-01-01\", \"price\": 400, " +
                "\"bandId\": 100, \"venueId\": 1000}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("rock concert"))
            .andExpect(jsonPath("$.band.id").value( 100))
            .andExpect(jsonPath("$.venue.id").isNotEmpty())
            .andExpect(jsonPath("$.venue.name").value("Budapest Park"))
            .andExpect(jsonPath("$.band.name").value("Muse"));
    }
}
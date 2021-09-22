package com.codecool.soundblastr.controller;

import com.codecool.soundblastr.entity.Event;
import com.codecool.soundblastr.entity.Venue;
import com.codecool.soundblastr.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EventRepository eventRepository;

    @Test
    void getAllEventsRoute_shouldReturnJsonWithListOfEvents_whenMultipleEventsArePresent() throws Exception {
        Event event = Event.builder().venue(
            Venue.builder().name("Budapest Park").build())
            .date(LocalDate.of(2021,12, 12)).title("rock concert").build();
        Event event2 = Event.builder().date(LocalDate.of(2021,12,12)).title("pop concert").build();
        Event event3 = Event.builder().date(LocalDate.of(2021,12,12)).title("electronic concert").build();
        eventRepository.saveAll(List.of(event, event2, event3));

        Mockito.when(eventRepository.findAll()).thenReturn(List.of(event, event2, event3));

        mockMvc.perform(MockMvcRequestBuilders
            .get("/event/all"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value("rock concert"))
            .andExpect(jsonPath("$[0].venue.name").value("Budapest Park"))
            .andExpect(jsonPath("$[1].title").value("pop concert"))
            .andExpect(jsonPath("$[2].title").value("electronic concert"));
    }
}
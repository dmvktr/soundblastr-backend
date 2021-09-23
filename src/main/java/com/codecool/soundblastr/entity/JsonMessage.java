package com.codecool.soundblastr.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class JsonMessage {

    private Status status;
    private String message;

}

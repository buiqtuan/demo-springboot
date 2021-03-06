package com.tawk.fun.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.*;
import java.time.format.DateTimeFormatter;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatInfo {
    @JsonProperty("websiteId")
    private String websiteId;

    @JsonProperty("date")
    private String date;

    @JsonProperty("chats")
    private String chats;

    @JsonProperty("missedChats")
    private String missedChats;

    public OffsetDateTime getDate(DateTimeFormatter formatter) {
        var ld = LocalDate.parse(this.date, formatter);

        return OffsetDateTime.of(ld, LocalTime.NOON, ZoneOffset.UTC);
    }
}

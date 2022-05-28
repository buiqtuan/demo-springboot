package com.tawk.fun.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;


@Data
@AllArgsConstructor
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
        var ldt = LocalDateTime.parse(this.date, formatter);

        return OffsetDateTime.of(ldt, ZoneOffset.UTC);
    }
}

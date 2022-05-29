package com.tawk.fun.controller.request;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.*;
import java.time.format.DateTimeFormatter;

@Data
public class GetChatInfoRequest {

    @Size(min = 10, max = 10)
    private String from;

    @Size(min = 10, max = 10)
    private String to;

    public OffsetDateTime getFrom(DateTimeFormatter formatter) {
        var ld = LocalDate.parse(this.from, formatter);

        return OffsetDateTime.of(ld, LocalTime.NOON, ZoneOffset.UTC);
    }

    public OffsetDateTime getTo(DateTimeFormatter formatter) {
        var ld = LocalDate.parse(this.to, formatter);

        return OffsetDateTime.of(ld, LocalTime.NOON, ZoneOffset.UTC);
    }
}

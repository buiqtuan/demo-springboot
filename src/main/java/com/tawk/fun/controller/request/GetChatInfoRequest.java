package com.tawk.fun.controller.request;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Data
public class GetChatInfoRequest {

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^0\\d{9}$")
    private String from;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^0\\d{9}$")
    private String to;

    public OffsetDateTime getFrom(DateTimeFormatter formatter) {
        var ldt = LocalDateTime.parse(this.from, formatter);

        return OffsetDateTime.of(ldt, ZoneOffset.UTC);
    }

    public OffsetDateTime getTo(DateTimeFormatter formatter) {
        var ldt = LocalDateTime.parse(this.to, formatter);

        return OffsetDateTime.of(ldt, ZoneOffset.UTC);
    }
}

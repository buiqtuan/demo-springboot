package com.tawk.fun.controller;

import com.tawk.fun.controller.request.GetChatInfoRequest;
import com.tawk.fun.controller.response.GetChatInfoResponse;
import com.tawk.fun.entity.ChatInfo;
import com.tawk.fun.usecase.FilterChatInfoBy;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController("api")
@Log4j2
public class WebChatInfoController {

    private final FilterChatInfoBy filterChatInfoBy;

    private final DateTimeFormatter dateTimeFormatterYYYYMMDD;

    public WebChatInfoController(FilterChatInfoBy filterChatInfoBy,
                                 DateTimeFormatter dateTimeFormatterYYYYMMDD) {
        this.filterChatInfoBy = filterChatInfoBy;
        this.dateTimeFormatterYYYYMMDD = dateTimeFormatterYYYYMMDD;
    }

    @GetMapping("getChatInfo")
    public ResponseEntity<?> getChatInfo(@Validated GetChatInfoRequest request) {

        List<ChatInfo> chatInfoByDateFilter;

        try {
            if (request.getFrom().isBlank() && request.getTo().isBlank()) {
                chatInfoByDateFilter = this.filterChatInfoBy.none();
            } else if (!request.getFrom().isBlank() && request.getTo().isBlank()) {
                chatInfoByDateFilter = this.filterChatInfoBy.fromDate(request.getFrom(this.dateTimeFormatterYYYYMMDD));
            } else if (request.getFrom().isBlank() && !request.getTo().isBlank()) {
                chatInfoByDateFilter = this.filterChatInfoBy.toDate(request.getTo(this.dateTimeFormatterYYYYMMDD));
            } else {
                chatInfoByDateFilter = this.filterChatInfoBy.fromDateToDate(
                        request.getFrom(this.dateTimeFormatterYYYYMMDD),
                        request.getTo(this.dateTimeFormatterYYYYMMDD)
                );
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new GetChatInfoResponse(ApiStatus.SUCCESS.getLabel(), chatInfoByDateFilter));
        } catch (Exception e) {
            log.error("api/getChatInfo", e);

            return ResponseEntity.internalServerError().body(e);
        }
    }
}

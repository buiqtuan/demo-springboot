package com.tawk.fun.controller;

import com.tawk.fun.controller.request.GetChatInfoRequest;
import com.tawk.fun.controller.response.GetChatInfoResponse;
import com.tawk.fun.entity.ChatInfo;
import com.tawk.fun.model.ChatInfoWithNoDateModel;
import com.tawk.fun.service.CachingService;
import com.tawk.fun.usecase.FilterChatInfoBy;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
@Log4j2
public class WebChatInfoController {

    private final FilterChatInfoBy filterChatInfoBy;

    private final DateTimeFormatter dateTimeFormatterYYYYMMDD;

    private final CachingService cachingService;

    public WebChatInfoController(FilterChatInfoBy filterChatInfoBy,
                                 DateTimeFormatter dateTimeFormatterYYYYMMDD,
                                 CachingService cachingService) {
        this.filterChatInfoBy = filterChatInfoBy;
        this.dateTimeFormatterYYYYMMDD = dateTimeFormatterYYYYMMDD;
        this.cachingService = cachingService;
    }

    @GetMapping("getChatInfo")
    public ResponseEntity<?> getChatInfo(@Validated GetChatInfoRequest request) {

        List<ChatInfo> chatInfoByDateFilter;

        try {
            if (request.getFrom() == null && request.getTo() == null) {
                chatInfoByDateFilter = this.cachingService.getAllChatInfoFromCache();
            } else if (request.getFrom() != null && request.getTo() == null) {
                chatInfoByDateFilter = this.filterChatInfoBy.fromDate(
                    this.cachingService.getAllChatInfoFromCache(),
                    request.getFrom(this.dateTimeFormatterYYYYMMDD)
                );
            } else if (request.getFrom() == null && request.getTo() != null) {
                chatInfoByDateFilter = this.filterChatInfoBy.toDate(
                    this.cachingService.getAllChatInfoFromCache(),
                    request.getTo(this.dateTimeFormatterYYYYMMDD)
                );
            } else {
                chatInfoByDateFilter = this.filterChatInfoBy.fromDateToDate(
                    this.cachingService.getAllChatInfoFromCache(),
                    request.getFrom(this.dateTimeFormatterYYYYMMDD),
                    request.getTo(this.dateTimeFormatterYYYYMMDD)
                );
            }

            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new GetChatInfoResponse(
                    ApiStatus.SUCCESS.getLabel(),
                    Integer.toString(chatInfoByDateFilter.size()),
                    chatInfoByDateFilter.stream()
                        .map(c -> new ChatInfoWithNoDateModel(c.getWebsiteId(), c.getChats(), c.getMissedChats()))
                        .collect(Collectors.toList()))
                );
        } catch (DateTimeException e) {
            log.error("api/getChatInfo", e);

            return ResponseEntity.badRequest().body("WRONG DATE FORMAT - SHOULD BE YYYY-MM-DD");
        } catch (Exception e) {
            log.error("api/getChatInfo", e);

            return ResponseEntity.internalServerError().body(e);
        }
    }
}

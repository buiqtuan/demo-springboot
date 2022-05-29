package com.tawk.fun.service;

import com.tawk.fun.domain.LoadChatDataDomain;
import com.tawk.fun.entity.ChatInfo;
import com.tawk.fun.usecase.FilterChatInfoBy;
import com.tawk.fun.usecase.GetDataFromCache;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class DataFilteringService implements FilterChatInfoBy {

    private final GetDataFromCache getDataFromCache;

    private final DateTimeFormatter dateTimeFormatter;

    public DataFilteringService(GetDataFromCache getDataFromCache,
                                DateTimeFormatter dateTimeFormatter) {
        this.getDataFromCache = getDataFromCache;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public List<ChatInfo> fromDateToDate(List<ChatInfo> chatInfoList, OffsetDateTime from, OffsetDateTime to) {
        var returnedList = new ArrayList<ChatInfo>();

        for (var chat : chatInfoList) {
            if (chat.getDate(this.dateTimeFormatter).compareTo(from) >= 0 && chat.getDate(this.dateTimeFormatter).compareTo(to) <= 0) {
                returnedList.add(chat);
            }
        }

        return returnedList;
    }

    @Override
    public List<ChatInfo> fromDate(List<ChatInfo> chatInfoList, OffsetDateTime from) {
        var returnedList = new ArrayList<ChatInfo>();

        for (var chat : chatInfoList) {
            if (chat.getDate(this.dateTimeFormatter).compareTo(from) >= 0) {
                returnedList.add(chat);
            }
        }

        return returnedList;
    }

    @Override
    public List<ChatInfo> toDate(List<ChatInfo> chatInfoList, OffsetDateTime to) {
        var returnedList = new ArrayList<ChatInfo>();

        for (var chat : chatInfoList) {
            if (chat.getDate(this.dateTimeFormatter).compareTo(to) <= 0) {
                returnedList.add(chat);
            }
        }

        return returnedList;
    }
}

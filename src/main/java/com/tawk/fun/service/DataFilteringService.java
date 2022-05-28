package com.tawk.fun.service;

import com.tawk.fun.entity.ChatInfo;
import com.tawk.fun.usecase.FilterChatInfoBy;
import com.tawk.fun.usecase.GetDataFromCache;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataFilteringService implements FilterChatInfoBy {

    private final GetDataFromCache getDataFromCache;

    private final DateTimeFormatter dateTimeFormatter;

    private final List<ChatInfo> chatInfoList;

    public DataFilteringService(GetDataFromCache getDataFromCache,
                                DateTimeFormatter dateTimeFormatter) {
        this.getDataFromCache = getDataFromCache;
        this.dateTimeFormatter = dateTimeFormatter;
        this.chatInfoList = this.getDataFromCache.getAllChatInfoFromCache();
    }

    @Override
    public List<ChatInfo> fromDateToDate(OffsetDateTime from, OffsetDateTime to) {

        var returnedList = new ArrayList<ChatInfo>();

        for (var chat : chatInfoList) {
            if (chat.getDate(this.dateTimeFormatter).compareTo(from) >= 0 || chat.getDate(this.dateTimeFormatter).compareTo(to) <= 0) {
                returnedList.add(chat);
            }
        }

        return returnedList;
    }

    @Override
    public List<ChatInfo> fromDate(OffsetDateTime from) {
        var returnedList = new ArrayList<ChatInfo>();

        for (var chat : chatInfoList) {
            if (chat.getDate(this.dateTimeFormatter).compareTo(from) >= 0) {
                returnedList.add(chat);
            }
        }

        return returnedList;
    }

    @Override
    public List<ChatInfo> toDate(OffsetDateTime to) {
        var returnedList = new ArrayList<ChatInfo>();

        for (var chat : chatInfoList) {
            if (chat.getDate(this.dateTimeFormatter).compareTo(to) <= 0) {
                returnedList.add(chat);
            }
        }

        return returnedList;
    }

    @Override
    public List<ChatInfo> none() {
        return this.chatInfoList;
    }
}

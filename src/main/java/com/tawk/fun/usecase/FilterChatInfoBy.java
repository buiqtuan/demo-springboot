package com.tawk.fun.usecase;

import com.tawk.fun.entity.ChatInfo;

import java.time.OffsetDateTime;
import java.util.List;

public interface FilterChatInfoBy {

    List<ChatInfo> fromDateToDate(OffsetDateTime from, OffsetDateTime to);

    List<ChatInfo> fromDate(OffsetDateTime from);

    List<ChatInfo> toDate(OffsetDateTime to);

    List<ChatInfo> none();
}

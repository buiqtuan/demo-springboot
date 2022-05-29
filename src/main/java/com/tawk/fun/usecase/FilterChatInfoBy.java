package com.tawk.fun.usecase;

import com.tawk.fun.entity.ChatInfo;

import java.time.OffsetDateTime;
import java.util.List;

public interface FilterChatInfoBy {

    List<ChatInfo> fromDateToDate(List<ChatInfo> chatInfoList, OffsetDateTime from, OffsetDateTime to);

    List<ChatInfo> fromDate(List<ChatInfo> chatInfoList,OffsetDateTime from);

    List<ChatInfo> toDate(List<ChatInfo> chatInfoList,OffsetDateTime to);
}

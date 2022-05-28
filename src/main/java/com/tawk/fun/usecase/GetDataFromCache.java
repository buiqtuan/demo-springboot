package com.tawk.fun.usecase;

import com.tawk.fun.entity.ChatInfo;

import java.util.List;

public interface GetDataFromCache {

    List<ChatInfo> getAllChatInfoFromCache();
}

package com.tawk.fun.domain;

import com.tawk.fun.entity.ChatInfo;

import java.util.List;

public interface LoadChatDataDomain {

    List<ChatInfo> loadAll();
}

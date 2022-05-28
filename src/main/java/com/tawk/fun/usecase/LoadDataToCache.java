package com.tawk.fun.usecase;

import com.tawk.fun.entity.ChatInfo;
import org.springframework.cache.annotation.CachePut;

import java.util.List;

public interface LoadDataToCache {

    List<ChatInfo> loadChatInfoToCache();
}

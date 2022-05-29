package com.tawk.fun.service;

import com.tawk.fun.domain.LoadChatDataDomain;
import com.tawk.fun.entity.ChatInfo;
import com.tawk.fun.usecase.GetDataFromCache;
import com.tawk.fun.usecase.LoadDataToCache;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CachingService implements GetDataFromCache, LoadDataToCache {

    private final LoadChatDataDomain loadChatDataDomain;

    private final CacheManager cacheManager;

    public CachingService(LoadChatDataDomain loadChatDataDomain,
                          CacheManager cacheManager) {
        this.loadChatDataDomain = loadChatDataDomain;
        this.cacheManager = cacheManager;
    }

    @Override
    public List<ChatInfo> getAllChatInfoFromCache() {
        try {
            Cache appCache = this.cacheManager.getCache("chatInfoList");

            var chatInfoList = (List<ChatInfo>) appCache.get("fullList").get();

            return chatInfoList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    @CachePut(value = "chatInfoList", key = "'fullList'")
    public List<ChatInfo> loadChatInfoToCache() {
        return this.loadChatDataDomain.loadAll();
    }
}

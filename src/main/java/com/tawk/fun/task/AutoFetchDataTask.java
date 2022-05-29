package com.tawk.fun.task;

import com.tawk.fun.usecase.LoadDataToCache;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@EnableScheduling
public class AutoFetchDataTask {

    private final LoadDataToCache loadDataToCache;

    public AutoFetchDataTask(LoadDataToCache loadDataToCache) {
        this.loadDataToCache = loadDataToCache;
    }

    @Scheduled(fixedRate = 30000)
    public void startFetching() {
        log.info(".........................START JOB FETCHING DATA FROM SOURCE.........................");

        try {
            var chatInforList = this.loadDataToCache.loadChatInfoToCache();
            log.info("chatInforList size:" + chatInforList.size());
        } catch (Exception e) {
            log.error("AutoFetchDataTask.startFetching",e);
        }

        log.info(".........................END JOB FETCHING DATA FROM SOURCE.........................");
    }
}

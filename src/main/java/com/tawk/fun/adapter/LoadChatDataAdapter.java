package com.tawk.fun.adapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tawk.fun.domain.LoadChatDataDomain;
import com.tawk.fun.entity.ChatInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class LoadChatDataAdapter implements LoadChatDataDomain {

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;
    private String dataSourceUrl;

    public LoadChatDataAdapter(RestTemplate restTemplate,
                               ObjectMapper objectMapper,
                               @Value("${tawk.web.chat.log.url}") String dataSourceUrl) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.dataSourceUrl = dataSourceUrl;
    }


    @Override
    public List<ChatInfo> loadAll() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(this.dataSourceUrl);
        URI uri = uriComponentsBuilder.build(true).toUri();
        log.info("(LoadChatDataAdapter.loadAll), url: {}", uri);

        try {
            var response = this.restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                String.class
            );

            if (response.getStatusCode() != HttpStatus.OK) {
                log.info("LoadChatDataAdapter.loadAll NOT OK", "StatusCode", response.getStatusCode());

                return new ArrayList<>();
            }

            if (response.getBody() == null) {
                log.info("LoadChatDataAdapter.loadAll is null");

                return new ArrayList<>();
            }

            log.info("LoadChatDataAdapter.loadAll", response.getBody());

            return this.objectMapper.readValue(response.getBody(), new TypeReference<List<ChatInfo>>(){});
        } catch (Exception e) {
            log.error("LoadChatDataAdapter.loadAll Exception", e);

            return new ArrayList<>();
        }
    }
}

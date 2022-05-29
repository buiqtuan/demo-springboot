package com.tawk.fun.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatInfoWithNoDateModel {

    @JsonProperty("websiteId")
    private String websiteId;

    @JsonProperty("chats")
    private String chats;

    @JsonProperty("missedChats")
    private String missedChats;
}

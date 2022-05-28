package com.tawk.fun.controller.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.tawk.fun.controller.ApiStatus;
import com.tawk.fun.entity.ChatInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetChatInfoResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("chatInfo")
    private List<ChatInfo> chatInfo;

}

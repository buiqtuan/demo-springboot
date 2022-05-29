package com.tawk.fun.controller.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.tawk.fun.controller.ApiStatus;
import com.tawk.fun.entity.ChatInfo;
import com.tawk.fun.model.ChatInfoWithNoDateModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetChatInfoResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("totalChat")
    private String totalChat;

    @JsonProperty("chatInfo")
    private List<ChatInfoWithNoDateModel> chatInfo;

}

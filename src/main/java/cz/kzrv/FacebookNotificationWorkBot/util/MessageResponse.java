package cz.kzrv.FacebookNotificationWorkBot.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.kzrv.FacebookNotificationWorkBot.DTO.MessageEvent;
import cz.kzrv.FacebookNotificationWorkBot.DTO.QuickReplies;
import cz.kzrv.FacebookNotificationWorkBot.DTO.user.Recipient;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponse {
    @JsonProperty("messaging_type")
    private String type;
    private Recipient recipient;
    private MessageEvent message;
    private String tag;
    @JsonProperty("quick_replies")
    private List<QuickReplies> quickReplies;


    public MessageResponse() {
    }

    public MessageResponse(Recipient recipient, MessageEvent message, String type) {
        this.recipient = recipient;
        this.message = message;
        this.type = type;
    }
}

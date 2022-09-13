package cz.kzrv.FacebookNotificationWorkBot.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import cz.kzrv.FacebookNotificationWorkBot.DTO.MessageEvent;
import cz.kzrv.FacebookNotificationWorkBot.DTO.user.Recipient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponse {
    @JsonFormat(pattern = "messaging_type")
    private String type;
    private Recipient recipient;
    private MessageEvent message;
    private String tag;


    public MessageResponse() {
    }

    public MessageResponse(Recipient recipient, MessageEvent message, String type) {
        this.recipient = recipient;
        this.message = message;
        this.type = type;
    }
}

package cz.kzrv.FacebookNotificationWorkBot.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import cz.kzrv.FacebookNotificationWorkBot.DTO.MessageEvent;
import cz.kzrv.FacebookNotificationWorkBot.DTO.user.Recipient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {
    private Recipient recipient;
    private MessageEvent message;
    @JsonFormat(pattern = "messaging_type")
    private String type;

    public MessageResponse() {
    }

    public MessageResponse(Recipient recipient, MessageEvent message, String type) {
        this.recipient = recipient;
        this.message = message;
        this.type = type;
    }
}

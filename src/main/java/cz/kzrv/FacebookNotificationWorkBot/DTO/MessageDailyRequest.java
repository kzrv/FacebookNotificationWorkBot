package cz.kzrv.FacebookNotificationWorkBot.DTO;

import cz.kzrv.FacebookNotificationWorkBot.DTO.user.Recipient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDailyRequest {
    private Recipient recipient;
    private MessageEvent message;

    public MessageDailyRequest() {
    }
}

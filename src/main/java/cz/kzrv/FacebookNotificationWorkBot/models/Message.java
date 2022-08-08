package cz.kzrv.FacebookNotificationWorkBot.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Message {
    private String sender;
    private String msg;

    public Message() {
    }
}

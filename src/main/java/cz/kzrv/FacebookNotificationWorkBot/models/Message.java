package cz.kzrv.FacebookNotificationWorkBot.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    private String sender;
    private String msg;

    public Message() {
    }
}

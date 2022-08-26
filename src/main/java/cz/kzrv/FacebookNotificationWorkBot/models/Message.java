package cz.kzrv.FacebookNotificationWorkBot.models;

import cz.kzrv.FacebookNotificationWorkBot.DTO.Optin;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    private String sender;
    private String msg;
    private Optin optin;

    public Message() {
    }
}

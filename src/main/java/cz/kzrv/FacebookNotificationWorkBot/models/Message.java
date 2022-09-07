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

    public Message(String sender, String msg, Optin optin) {
        this.sender = sender;
        this.msg = msg;
        this.optin = optin;
    }
}

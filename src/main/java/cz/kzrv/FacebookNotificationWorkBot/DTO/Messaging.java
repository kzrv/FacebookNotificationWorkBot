package cz.kzrv.FacebookNotificationWorkBot.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import cz.kzrv.FacebookNotificationWorkBot.DTO.user.Recipient;
import cz.kzrv.FacebookNotificationWorkBot.DTO.user.Sender;

import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Messaging {
    private Sender sender;
    private Recipient recipient;
    private Date timestamp;
    private MessageEvent message;
    private Optin optin;

    public Messaging() {
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public MessageEvent getMessage() {
        return message;
    }

    public void setMessage(MessageEvent message) {
        this.message = message;
    }

    public Optin getOptin() {
        return optin;
    }

    public void setOptin(Optin optin) {
        this.optin = optin;
    }
}

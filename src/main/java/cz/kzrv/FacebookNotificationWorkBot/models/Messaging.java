package cz.kzrv.FacebookNotificationWorkBot.models;

import cz.kzrv.FacebookNotificationWorkBot.models.user.Recipient;
import cz.kzrv.FacebookNotificationWorkBot.models.user.Sender;

import java.util.Date;

public class Messaging {
    private Sender sender;
    private Recipient recipient;
    private Date timestamp;
    private MessageEvent message;

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
}

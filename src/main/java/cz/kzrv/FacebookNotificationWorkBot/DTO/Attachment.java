package cz.kzrv.FacebookNotificationWorkBot.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attachment {
    private String type;
    private Payload payload;
    public Attachment() {
    }

    public Attachment(String type, Payload payload) {
        this.type = type;
        this.payload = payload;
    }
}

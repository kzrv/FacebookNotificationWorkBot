package cz.kzrv.FacebookNotificationWorkBot.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payload {
    @JsonProperty(value = "template_type")
    private String templateType;
    @JsonProperty(value = "image_url")
    private String imageUrl;
    private String title;
    private String payload;
    @JsonProperty(value = "notification_messages_frequency")
    private String notifMsgFrequency;
    @JsonProperty(value = "notification_messages_reoptin")
    private String notifMsgReoptin;
    @JsonProperty(value = "notification_messages_timezone")
    private String notifMsgTimezone;

    public Payload() {
    }

}

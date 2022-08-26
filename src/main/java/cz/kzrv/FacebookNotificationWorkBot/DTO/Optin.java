package cz.kzrv.FacebookNotificationWorkBot.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Optin {
    private String type;
    private String payload;
    @JsonProperty(value = "notification_messages_token")
    private String notifToken;
    @JsonProperty(value = "notification_messages_frequency")
    private String notifFrequency;
    @JsonProperty(value = "notification_messages_timezone")
    private String notifTimezone;
    @JsonProperty(value = "token_expiry_timestamp")
    private String tokenTime;
    @JsonProperty(value = "user_token_status")
    private String tokenStatus;
    @JsonProperty("notification_messages_status")
    private String messageStatus;

    public Optin() {
    }

    @Override
    public String toString() {
        return "Optin{" +
                "type='" + type + '\'' +
                ", payload='" + payload + '\'' +
                ", notifToken='" + notifToken + '\'' +
                ", notifFrequency='" + notifFrequency + '\'' +
                ", notifTimezone='" + notifTimezone + '\'' +
                ", tokenTime='" + tokenTime + '\'' +
                ", tokenStatus='" + tokenStatus + '\'' +
                ", messageStatus='" + messageStatus + '\'' +
                '}';
    }
}

package cz.kzrv.FacebookNotificationWorkBot.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.AccessType;
/**
 "notification_messages_token": "NOTIFICATION-MESSAGES-TOKEN",
 "recipient_id": "PSID",
 "payload": "USER-DEFINED-PAYLOAD",
 "notification_messages_frequency": "MONTHLY",
 "creation_timestamp": "TIMESTAMP",
 "token_expiry_timestamp": "TIMESTAMP",
 "user_token_status": "REFRESHED",
 "notification_messages_reoptin": "ENABLED",
 "notification_messages_timezone": "TIMEZONE-ID"
 */
@Getter
@Setter
public class TokenInfo {
    @JsonProperty("recipient_id")
    private String id;
    @JsonProperty("token_expiry_timestamp")
    private String endOfToken;

    public TokenInfo() {
    }
}



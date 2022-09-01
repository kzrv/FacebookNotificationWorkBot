package cz.kzrv.FacebookNotificationWorkBot.DTO.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Recipient {
    private String id;
    @JsonProperty(value = "notification_messages_token")
    private String notMessageToken;

    public Recipient() {
    }


    public Recipient(String id) {
        this.id = id;
    }
}

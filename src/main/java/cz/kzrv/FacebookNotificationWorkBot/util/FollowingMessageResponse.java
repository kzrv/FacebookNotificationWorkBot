package cz.kzrv.FacebookNotificationWorkBot.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.kzrv.FacebookNotificationWorkBot.DTO.user.Recipient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowingMessageResponse {
    @JsonProperty(value = "developer_action")
    private String devAction;
    private Recipient recipient;

    public FollowingMessageResponse() {
    }
}

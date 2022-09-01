package cz.kzrv.FacebookNotificationWorkBot.util;

import cz.kzrv.FacebookNotificationWorkBot.DTO.user.Recipient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowingMessageResponse {
    private String devAction;
    private Recipient recipient;

    public FollowingMessageResponse() {
    }
}

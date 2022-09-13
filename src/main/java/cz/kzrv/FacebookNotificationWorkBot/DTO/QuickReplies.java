package cz.kzrv.FacebookNotificationWorkBot.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuickReplies {
    @JsonProperty("content_type")
    private String contentType;
    private String title;
    private String payload;
    @JsonProperty("image_url")
    private String imageUrl;

    public QuickReplies() {
    }

}

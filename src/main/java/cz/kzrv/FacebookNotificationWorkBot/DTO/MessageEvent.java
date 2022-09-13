package cz.kzrv.FacebookNotificationWorkBot.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageEvent {
    private String mid; // id of message
    private String text;
    private Attachment attachment;
    @JsonProperty("quick_replies")
    private List<QuickReplies> quickReplies;

    public MessageEvent() {
    }

    public MessageEvent(String text) {
        this.text = text;
    }

}

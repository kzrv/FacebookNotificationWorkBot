package cz.kzrv.FacebookNotificationWorkBot.DTO;

public class MessageEvent {
    private String mid; // id of message
    private String text;

    public MessageEvent() {
    }

    public MessageEvent(String text) {
        this.text = text;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

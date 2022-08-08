package cz.kzrv.FacebookNotificationWorkBot.DTO.user;

public class Recipient {
    private String id;

    public Recipient() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Recipient(String id) {
        this.id = id;
    }
}

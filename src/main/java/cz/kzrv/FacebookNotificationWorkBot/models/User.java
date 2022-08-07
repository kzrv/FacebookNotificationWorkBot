package cz.kzrv.FacebookNotificationWorkBot.models;

import java.util.Date;
import java.util.List;

public class User {
    private Date time;
    private int id;
    private List<Messaging> messaging;

    public User() {
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<Messaging> getMessaging() {
        return messaging;
    }

    public void setMessaging(List<Messaging> messaging) {
        this.messaging = messaging;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

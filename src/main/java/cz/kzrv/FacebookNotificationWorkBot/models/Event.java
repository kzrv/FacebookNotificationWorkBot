package cz.kzrv.FacebookNotificationWorkBot.models;

import java.util.List;

public class Event {
    private String object;
    private List<User> entry;

    public Event() {
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<User> getEntry() {
        return entry;
    }

    public void setEntry(List<User> entry) {
        this.entry = entry;
    }

}

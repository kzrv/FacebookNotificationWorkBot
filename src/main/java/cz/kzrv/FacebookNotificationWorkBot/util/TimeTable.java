package cz.kzrv.FacebookNotificationWorkBot.util;

public enum TimeTable {
    SHIFT1("10:30","16:00"),
    SHIFT2("16:30","23:00"),
    SHIFT3("11:00","15:00"),
    SHIFT4("11:00","14:00"),
    SHIFT5("18:30","20:00"),
    SHIFT6("18:00","23:00");

    private String begin;
    private String end;
    private int id;

    TimeTable(String begin, String end) {
        this.id = id;
        this.begin = begin;
        this.end = end;
    }

    public static TimeTable getById(int id){
        return TimeTable.values()[id];
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

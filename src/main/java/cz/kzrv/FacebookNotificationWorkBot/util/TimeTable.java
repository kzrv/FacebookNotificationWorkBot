package cz.kzrv.FacebookNotificationWorkBot.util;

public enum TimeTable {
    MANAZER("9:30","24:00"),
    PIZZAR_HLAVNI("9:30","24:00"),
    PIZZAR("16:30","20:00"),
    RIDIC1("10:00","24:00"),
    RIDIC2("11:00","14:00"),
    RIDIC3("18:00","21:30"),
    ZDOBENI("11:00","14:00"),
    SKOLENI("17:00","21:00");

    private String begin;
    private String end;

    TimeTable(String begin, String end) {
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


}

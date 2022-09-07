package cz.kzrv.FacebookNotificationWorkBot.util;

public enum TimeTable {
    MANAZER("9:30","24/23"),
    MANAZER_1("DELENA","DELENA"),

    PIZZAR_HLAVNI("9:30","24/23"),
    PIZZAR_HLAVNI_1("DELENA","DELENA"),

    PIZZAR("16:30","21:00"),

    RIDIC1("10:00","24:00"),
    RIDIC1_1("DELENA","DELENA"),

    RIDIC2("16:30","24/23"),
    RIDIC2_1("11:00","14:00"),
    RIDIC2_2("DELENA","DELENA"),

    RIDIC3("18:00","21:30"),

    ZDOBENI("16:30","22:00/22:30"),
    ZDOBENI_1("11:00","14:00"),

    SKOLENI("DELENA","DELENA");



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


package cz.kzrv.FacebookNotificationWorkBot.util;

public enum TimeTable {
    MANAZER("9:30","24/23", "MANAŽER"),
    MANAZER_1("DELENA","DELENA", "MANAŽER"),

    PIZZAR_HLAVNI("9:30","24/23", "HLAVNÍ PIZZAŘ"),
    PIZZAR_HLAVNI_1("DELENA","DELENA", "HLAVNÍ PIZZAŘ"),

    PIZZAR("16:30","21:00", "PIZZAŘ"),

    RIDIC1("10:00","24:00", "ŘIDIČ 1"),
    RIDIC1_1("DELENA","DELENA", "ŘIDIČ 1"),

    RIDIC2("16:30","24/23", "ŘIDIČ 2"),
    RIDIC2_1("11:00","14:00", "ŘIDIČ 2"),
    RIDIC2_2("DELENA","DELENA", "ŘIDIČ 2"),

    RIDIC3("18:00","21:30", "ŘIDIČ 3"),

    ZDOBENI("16:30","22:00/22:30", "ZDOBENÍ"),
    ZDOBENI_1("11:00","14:00", "ZDOBENÍ"),

    SKOLENI("DELENA","DELENA", "ŠKOLENÍ");



    private String begin;
    private String end;
    private String title;

    TimeTable(String begin, String end, String title) {
        this.begin = begin;
        this.end = end;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}


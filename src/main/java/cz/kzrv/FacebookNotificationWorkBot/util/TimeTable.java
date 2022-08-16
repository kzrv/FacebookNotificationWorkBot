package cz.kzrv.FacebookNotificationWorkBot.util;

import java.util.Arrays;

public enum TimeTable {
    MANAZER(0,0,"9:30","24:00"),
    MANAZER_1(0,1,"9:30","24:00"),
    MANAZER_2(0,2,"9:30","24:00"),

    PIZZAR_HLAVNI(1,0,"9:30","24:00"),
    PIZZAR_HLAVNI_1(1,1,"9:30","24:00"),
    PIZZAR_HLAVNI_2(1,2,"9:30","24:00"),

    PIZZAR(2,0,"16:30","20:00"),
    PIZZAR_1(2,1,"16:30","20:00"),
    PIZZAR_2(2,2,"16:30","20:00"),

    RIDIC1(3,0,"10:00","24:00"),
    RIDIC1_1(3,1,"10:00","16:30"),
    RIDIC1_2(3,2,"16:30","24:00"),

    RIDIC2(4,0,"16:30","24:00"),
    RIDIC2_1(4,1,"11:00","14:00"),
    RIDIC2_2(4,2,"16:30","23:00"),

    RIDIC3(5,0,"18:00","24:00"),
    RIDIC3_1(5,1,"18:00","21:30"),
    RIDIC_2(5,2,"18:00","21:30"),

    ZDOBENI(6,0,"16:30","22:00"),
    ZDOBENI_1(6,1,"11:00","14:00"),
    ZDOBENI_2(6,2,"16:30","22:00"),

    SKOLENI(7,0,"17:00","21:00"),
    SKOLENI_1(7,1,"17:00","21:00"),
    SKOLENI_2(7,2,"17:00","21:00");

    private int id;
    private int shift;
    private String begin;
    private String end;

    TimeTable(int id, int shift,String begin, String end) {
        this.begin = begin;
        this.end = end;
        this.id = id;
        this.shift = shift;
    }

    public static TimeTable getById(int id, int shift){
        return Arrays.stream(TimeTable.values()).filter(el->el.id==id && el.shift==shift).findFirst().orElse(null);
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

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }
}

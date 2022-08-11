package cz.kzrv.FacebookNotificationWorkBot.util;

import java.util.Arrays;
import java.util.Objects;

public enum Month {
    LEDEN(1,"LEDEN"),
    UNOR(2,"ÚNOR"),
    BREZEN(3,"BŘEZEN"),
    DUBEN(4,"DUBEN"),
    KVETEN(5,"KVĚTEN"),
    CERVEN(6,"ČERVEN"),
    CERVENEC(7,"ČERVENEC"),
    SRPEN(8,"SRPEN"),
    ZARI(9,"ZÁŘÍ"),
    RIJEN(10,"ŘÍJEN"),
    LISTOPAD(11,"LISTOPAD"),
    PROSINEC(12,"PROSINEC");

    private String name;
    private int countOfMonth;

    Month(int countOfMonth,String name) {
        this.name = name;
        this.countOfMonth = countOfMonth;
    }
    public static String getMonthName(int count){
        Month name = Arrays.stream(Month.values()).filter(s->s.countOfMonth==count).findFirst().orElse(null);
        return Objects.requireNonNull(name).name;
    }

    public String getName() {
        return name;
    }

    public int getCountOfMonth() {
        return countOfMonth;
    }
}

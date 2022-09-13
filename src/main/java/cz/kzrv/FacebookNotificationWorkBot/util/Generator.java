package cz.kzrv.FacebookNotificationWorkBot.util;

import java.util.ArrayList;

public class Generator {
    public static String uniqueCode(String msg){
        return randomWord() +
                randomWord() +
                msg +
                randomWord() +
                randomWord() +
                randomWord();
    }
    private static String randomWord(){
        ArrayList<String> alphaNum = new ArrayList<String>();

        for (char c = 'A';c<= 'z';c++){
            String s = new String();
            s +=c;
            alphaNum.add(s);
            if (c == 'Z') c = 'a'-1;
        }

        for (int c = 0;c<10;c++){
            String s = new String();
            s +=c;
            alphaNum.add(s);
        }
        return (alphaNum.get((int)(Math.random()*alphaNum.size())));
    }
}

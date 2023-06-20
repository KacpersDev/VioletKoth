package org.loopstudios.utils;

public class TimeUtil {

    public static String toMinutes(int time) {
        if (time < 60) {
            return String.valueOf(time);
        } else {
            int minutes = time / 60;
            int seconds = time - (minutes * 60);
            return minutes + ":" + seconds;
        }
    }
}

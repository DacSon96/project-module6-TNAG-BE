package com.codegym.project.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Timer {
    public static LocalDateTime getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        return now;
    }
}

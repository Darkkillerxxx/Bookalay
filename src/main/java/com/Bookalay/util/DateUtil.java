package com.Bookalay.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String formatWithSuffix(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isEmpty()) return "";

        LocalDateTime date =
            LocalDateTime.parse(dateTimeStr.replace(" ", "T"));

        int day = date.getDayOfMonth();

        String suffix;
        if (day >= 11 && day <= 13) {
            suffix = "th";
        } else {
            switch (day % 10) {
                case 1: suffix = "st"; break;
                case 2: suffix = "nd"; break;
                case 3: suffix = "rd"; break;
                default: suffix = "th";
            }
        }

        DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("MMMM yyyy");

        return day + suffix + " " + date.format(formatter);
    }
    
    public static String formatWithSuffix(LocalDateTime dateTime) {
        if (dateTime == null) return "";

        int day = dateTime.getDayOfMonth();

        String suffix;
        if (day >= 11 && day <= 13) {
            suffix = "th";
        } else {
            switch (day % 10) {
                case 1: suffix = "st"; break;
                case 2: suffix = "nd"; break;
                case 3: suffix = "rd"; break;
                default: suffix = "th";
            }
        }

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MMMM yyyy");

        return day + suffix + " " + dateTime.format(formatter);
    }
}

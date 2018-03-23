package cn.edu.nju.tickets.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class DateUtil {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd");

    public static String convertDateToString(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static Date convertStringToDate(String dateStr) {
        String[] dateInfo = dateStr.split("-");
        LocalDateTime localDateTime = LocalDateTime.of(
                Integer.valueOf(dateInfo[0]),
                Integer.valueOf(dateInfo[1]),
                Integer.valueOf(dateInfo[2]),
                0 ,0, 0
        );
        return Timestamp.valueOf(localDateTime);
    }

    public static void main(String[] args) {
        Date date = convertStringToDate("2018-3-4");
        System.out.println(DATE_FORMAT.format(date));
    }
}

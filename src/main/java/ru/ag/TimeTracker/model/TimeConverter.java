package ru.ag.TimeTracker.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConverter {

    public Date strToDate(String value) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = format.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Long minuts(Task task) {
        long timeLong = task.getDateEnd().getTime() - task.getDateStart().getTime();
        timeLong /= (60 * 1000);
        return timeLong;
    }
}

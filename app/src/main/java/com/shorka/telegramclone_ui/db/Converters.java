package com.shorka.telegramclone_ui.db;

import android.arch.persistence.room.TypeConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kyrylo Avramenko on 8/28/2018.
 */
public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }


    private static DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT);

    public static String dateToHourAndMinute(Date date) {
        return date == null ? null : df.format(date);
    }

    public static String dateToHourAndMinute(long date) {
        return  df.format(date);
    }


}

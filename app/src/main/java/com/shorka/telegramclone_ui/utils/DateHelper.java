package com.shorka.telegramclone_ui.utils;

import com.shorka.telegramclone_ui.db.Converters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Kyrylo Avramenko on 8/29/2018.
 */
public class DateHelper {

    //1000 * 60 * 60 * 24
    private static final long MILLIS_IN_DAY = 86400000;
    private static final long MILLIS_IN_WEEK =  MILLIS_IN_DAY*7;
    private static Calendar c2;

    public static String getProperDateFormat(Date date) {

        long diff = Calendar.getInstance().getTimeInMillis() - date.getTime();


        if (c2 == null)
            c2 = Calendar.getInstance();

        //if this message was sent today
        if(diff< MILLIS_IN_DAY ){
            return Converters.dateToHourAndMinute(date);
        }

        //if this message was sent on this week
        else if(diff> MILLIS_IN_DAY && diff<MILLIS_IN_WEEK) {
            c2.setTime(date);
            return c2.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US);
        }

        //If this message was sent earlier that last week
         else {
            SimpleDateFormat sd = new SimpleDateFormat("MMM dd", Locale.US);
            return sd.format(date);
        }
    }
}

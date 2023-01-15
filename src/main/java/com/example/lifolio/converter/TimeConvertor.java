package com.example.lifolio.converter;

import com.example.lifolio.dto.planning.PlanningRes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeConvertor {

    public static PlanningRes.TimeRes getThisWeek() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calender=Calendar.getInstance();

        String date=sdf.format(calender.getTime());

        Date date2 = new Date();

        try{
            date2 = sdf.parse(date);
        }
        catch(ParseException ignored){
        }

        Calendar cal = Calendar.getInstance(Locale.KOREA);
        cal.setTime(date2);

        cal.add(Calendar.DATE, 1 - cal.get(Calendar.DAY_OF_WEEK));
        LocalDateTime startDate= LocalDateTime.parse(sdf.format(cal.getTime())+"T00:00:00");

        cal.setTime(date2);

        cal.add(Calendar.DATE, 7 - cal.get(Calendar.DAY_OF_WEEK));
        LocalDateTime finishDate= LocalDateTime.parse(sdf.format(cal.getTime())+"T23:59:59");

        return PlanningRes.TimeRes.builder().startTime(startDate).finishTime(finishDate).build();
    }

    public static PlanningRes.TimeRes getToday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calender=Calendar.getInstance();

        String date=sdf.format(calender.getTime());


        LocalDateTime startDate= LocalDateTime.parse(date+"T00:00:00");
        LocalDateTime finishDate= LocalDateTime.parse(date+"T23:59:59");

        return PlanningRes.TimeRes.builder().startTime(startDate).finishTime(finishDate).build();

    }

    public static PlanningRes.TimeRes getThisMonth() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calender=Calendar.getInstance();

        String date=sdf.format(calender.getTime());

        String year=date.substring(0,4);

        String month=date.substring(5,7);

        calender.set(Integer.parseInt(year),Integer.parseInt(month)-1,1);
        LocalDateTime startDate= LocalDateTime.parse(year+"-"+month+"-"+"01"+"T00:00:00");

        LocalDateTime finishDate= LocalDateTime.parse(year+"-"+month+"-"+Integer.toString(calender.getActualMaximum(Calendar.DAY_OF_MONTH))+"T23:59:59");


        return PlanningRes.TimeRes.builder().startTime(startDate).finishTime(finishDate).build();
    }
}

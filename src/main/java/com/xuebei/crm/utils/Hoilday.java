package com.xuebei.crm.utils;

import java.util.List;
import  java.util.Arrays;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Hoilday {
    private static List<String> lawHolidays = Arrays.asList("2018-12-31","2019-01-01","2019-02-04","2019-02-05","2019-02-06","2019-02-07","2019-02-08","2019-04-05","2019-04-29",
            "2019-04-30","2019-05-01","2019-06-07","2019-09-13","2019-10-01","2019-10-02","2019-10-03","2019-10-04","2019-10-07");
    private static List<String> extraWorkdays = Arrays.asList("2018-12-29","2019-02-02","2019-02-03","2019-04-27","2019-04-28","2019-09-29","2019-10-05","2019-10-06","2019-10-12");

    public static boolean isLawHoliday(String calendar) {
        if (lawHolidays.contains(calendar)) {
            return true;
        }
        return false;
    }

    public static boolean isWeekends(String calendar) throws Exception {
        // 先将字符串类型的日期转换为Calendar类型
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(calendar);
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        if (ca.get(Calendar.DAY_OF_WEEK) == 1 || ca.get(Calendar.DAY_OF_WEEK) == 7) {
            return true;
        }
        return false;
    }

    public static boolean isExtraWorkday(String calendar) throws Exception {
        if (extraWorkdays.contains(calendar)) {
            return true;
        }
        return false;
    }

    public static boolean isHoliday(String calendar) throws Exception {
        // 首先法定节假日必定是休息日
        if (isLawHoliday(calendar)) {
            return true;
        }
        // 排除法定节假日外的非周末必定是工作日
        if (!isWeekends(calendar)) {
            return false;
        }
        // 所有周末中只有非补班的才是休息日
        if (isExtraWorkday(calendar)) {
            return false;
        }
        return true;
    }

    public  static  boolean isHoliday(Date calendar) throws Exception
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(calendar);
        return isHoliday(dateString);
    }
}

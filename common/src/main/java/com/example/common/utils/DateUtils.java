package com.example.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ChengJinbao
 * 2019年11月15日
 */
public class DateUtils {
    /**
     * 格式化时间
     *
     * @param date
     * @param format
     * @return
     */
    public static String getFormatDateTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return sdf.format(date);
    }

    /**
     * 十位时间戳字符串转年月
     * @param time
     * @return
     */
    public static String FormatYearMonDay(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(Long.parseLong(time)));
    }

    /**
     * 那年今日
     *
     * @param reduceYear
     * @return
     */
    public static Date getThatDayToady(int reduceYear) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        //表示你需要减少的年数
        c.add(Calendar.YEAR, reduceYear);
        c.add(Calendar.MONTH, 0);
        c.add(Calendar.DATE, 0);
        return c.getTime();
    }

    public static Date getSixYearAgo() {
        int curryYear = Calendar.getInstance().get(Calendar.YEAR);
        int defaultYear = curryYear - 6;
        String time = defaultYear + "年06月01日";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        try {
            return simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getThatDayToady(-6);
    }


    public static long getTimeStump(Date date) {
        return date.getTime();
    }


    public static String FormatYearMonDay(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(time));
    }
}

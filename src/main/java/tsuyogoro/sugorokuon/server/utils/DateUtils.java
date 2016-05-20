package tsuyogoro.sugorokuon.server.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class DateUtils {

    static final private String JST = "JST";

    /**
     * 現在時刻のCalendarをJSTで取得
     *
     * @return
     */
    public static Calendar getCalendarInJst() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone(JST));
        return c;
    }

    /**
     * JSTで出力するSimpleDateFormatを取得
     *
     * @param pattern
     * @return
     */
    public static SimpleDateFormat getSimpleDateFormatInJst(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        TimeZone timeZone = TimeZone.getTimeZone(JST);
        format.setTimeZone(timeZone);
        return format;
    }

}

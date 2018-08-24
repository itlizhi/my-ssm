package com.enreach.ssm.utils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import java.text.ParseException;
import java.util.Date;

public class DateFormatUtil {

    /**
     * 以T分隔日期和时间，并带时区信息，符合ISO8601规范
     */
    public static final String PATTERN_ISO = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ";
    public static final String PATTERN_ISO_YMD_HMS = "yyyy-MM-dd'T'HH:mm:ssZZ";

    /**
     * 以空格分隔日期和时间，不带时区信息
     */
    public static final String PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String PATTERN_DEFAULT_YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DEFAULT_YMD_HM = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_DEFAULT_YMD = "yyyy-MM-dd";

    // 使用工厂方法FastDateFormat.getInstance(), 从缓存中获取实例

    /**
     * 以T分隔日期和时间，并带时区信息，符合ISO8601规范
     */
    public static final FastDateFormat ISO_FORMAT = FastDateFormat.getInstance(PATTERN_ISO);
    public static final FastDateFormat ISO_YMD_HMS_FORMAT = FastDateFormat.getInstance(PATTERN_ISO_YMD_HMS);

    /**
     * 以空格分隔日期和时间，不带时区信息
     */
    public static final FastDateFormat DEFAULT_FORMAT = FastDateFormat.getInstance(PATTERN_DEFAULT);
    public static final FastDateFormat DEFAULT_YMD_HMS_FORMAT = FastDateFormat.getInstance(PATTERN_DEFAULT_YMD_HMS);
    public static final FastDateFormat DEFAULT_YMD_HM_FORMAT = FastDateFormat.getInstance(PATTERN_DEFAULT_YMD_HM);
    public static final FastDateFormat DEFAULT_YMD_FORMAT = FastDateFormat.getInstance(PATTERN_DEFAULT_YMD);

    /**
     * 分析日期字符串, 仅用于pattern不固定的情况.
     * <p>
     * 否则直接使用DateFormats中封装好的FastDateFormat.
     * <p>
     * FastDateFormat.getInstance()已经做了缓存，不会每次创建对象，但直接使用对象仍然能减少在缓存中的查找.
     */
    public static Date parseDate(String pattern, String dateString) throws ParseException {
        return FastDateFormat.getInstance(pattern).parse(dateString);
    }

    /**
     * 格式化日期, 仅用于pattern不固定的情况.
     * <p>
     * 否则直接使用本类中封装好的FastDateFormat.
     * <p>
     * FastDateFormat.getInstance()已经做了缓存，不会每次创建对象，但直接使用对象仍然能减少在缓存中的查找.
     */
    public static String formatDate(String pattern, Date date) {
        return FastDateFormat.getInstance(pattern).format(date);
    }

    /**
     * 格式化日期, 仅用于不固定pattern不固定的情况.
     * <p>
     * 否否则直接使用本类中封装好的FastDateFormat.
     * <p>
     * FastDateFormat.getInstance()已经做了缓存，不会每次创建对象，但直接使用对象仍然能减少在缓存中的查找.
     */
    public static String formatDate(String pattern, long date) {
        return FastDateFormat.getInstance(pattern).format(date);
    }

    /////// 格式化间隔时间/////////

    /**
     * 按HH:mm:ss.SSS格式，格式化时间间隔.
     * <p>
     * endDate必须大于startDate，间隔可大于1天，
     *
     * @see DurationFormatUtils
     */
    public static String formatDuration(Date startDate, Date endDate) {
        return DurationFormatUtils.formatDurationHMS(endDate.getTime() - startDate.getTime());
    }

    /**
     * 按HH:mm:ss.SSS格式，格式化时间间隔
     * <p>
     * 单位为毫秒，必须大于0，可大于1天
     *
     * @see DurationFormatUtils
     */
    public static String formatDuration(long durationMillis) {
        return DurationFormatUtils.formatDurationHMS(durationMillis);
    }

    /**
     * 按HH:mm:ss格式，格式化时间间隔
     * <p>
     * endDate必须大于startDate，间隔可大于1天
     *
     * @see DurationFormatUtils
     */
    public static String formatDurationOnSecond(Date startDate, Date endDate) {
        return DurationFormatUtils.formatDuration(endDate.getTime() - startDate.getTime(), "HH:mm:ss");
    }

    /**
     * 按HH:mm:ss格式，格式化时间间隔
     * <p>
     * 单位为毫秒，必须大于0，可大于1天
     *
     * @see DurationFormatUtils
     */
    public static String formatDurationOnSecond(long durationMillis) {
        return DurationFormatUtils.formatDuration(durationMillis, "HH:mm:ss");
    }

    //////// 打印用于页面显示的用户友好，与当前时间比的时间差

    /**
     * 打印用户友好的，与当前时间相比的时间差，如刚刚，5分钟前，今天XXX，昨天XXX
     * <p>
     * copy from AndroidUtilCode
     */
    public static String formatFriendlyTimeSpanByNow(Date date) {
        return formatFriendlyTimeSpanByNow(date.getTime());
    }

    /**
     * 打印用户友好的，与当前时间相比的时间差，如刚刚，5分钟前，今天XXX，昨天XXX
     * <p>
     * copy from AndroidUtilCode
     */
    public static String formatFriendlyTimeSpanByNow(long timeStampMillis) {
        long now = System.currentTimeMillis();
        long span = now - timeStampMillis;
        if (span < 0) {
            // 'c' 日期和时间，被格式化为 "%ta %tb %td %tT %tZ %tY"，例如 "Sun Jul 20 16:17:00 EDT 1969"。
            return String.format("%tc", timeStampMillis);
        }
        if (span < DateUtil.MILLIS_PER_SECOND) {
            return "刚刚";
        } else if (span < DateUtil.MILLIS_PER_MINUTE) {
            return String.format("%d秒前", span / DateUtil.MILLIS_PER_SECOND);
        } else if (span < DateUtil.MILLIS_PER_HOUR) {
            return String.format("%d分钟前", span / DateUtil.MILLIS_PER_MINUTE);
        }
        // 获取当天00:00
        long wee = DateUtil.beginOfDate(new Date(now)).getTime();
        if (timeStampMillis >= wee) {
            // 'R' 24 小时制的时间，被格式化为 "%tH:%tM"
            return String.format("今天%tR", timeStampMillis);
        } else if (timeStampMillis >= wee - DateUtil.MILLIS_PER_DAY) {
            return String.format("昨天%tR", timeStampMillis);
        } else {
            // 'F' ISO 8601 格式的完整日期，被格式化为 "%tY-%tm-%td"。
            return String.format("%tF", timeStampMillis);
        }
    }
}

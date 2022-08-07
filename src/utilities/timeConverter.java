package utilities;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Time converter class is used to transform times retrieved from the database and sent to the database
 */
public class timeConverter {

    ZoneId currentZone = ZoneId.systemDefault();

    /**
     * changes local time to utc
     * @param dateTime local time
     * @return utc time
     */
    public static String toUTC(String dateTime) {
        Timestamp timestamp = Timestamp.valueOf(dateTime);
        LocalDateTime ldt = timestamp.toLocalDateTime();
        ZonedDateTime zdt = ldt.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utczdt = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime ldtIn = utczdt.toLocalDateTime();
        return ldtIn.format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));
    }

    /**
     * changes utc time to local
     * @param dateTime utc
     * @return local time
     */
    public static String toLocal(String dateTime) {
        Timestamp timestamp = Timestamp.valueOf(dateTime);
        LocalDateTime ldt = timestamp.toLocalDateTime();
        ZonedDateTime zdtOut = ldt.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdtOutToLocalTZ = zdtOut.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        LocalDateTime ldOutFinal = zdtOutToLocalTZ.toLocalDateTime();
        return ldOutFinal.format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));
    }

    /**
     * changes business starting hours to local
     * @return start giyrs
     */
    public static int setStartHours(){

        int bizStartUTC = 7;

        TimeZone tz = TimeZone.getDefault();
        Calendar calendar = GregorianCalendar.getInstance(tz);
        int offset = tz.getOffset(calendar.getTimeInMillis());
        if (offset != 0) {
            int hours = Math.abs((offset / (60 * 1000)) / 60) - 5;
            return bizStartUTC - hours;
        }else{
            return bizStartUTC;
        }

    }

    /**
     * changes business ending hours to local
     * @return end hours
     */
    public static int setEndHours() {

        int bizStartUTC = 22;

        TimeZone tz = TimeZone.getDefault();
        Calendar calendar = GregorianCalendar.getInstance(tz);
        int offset = tz.getOffset(calendar.getTimeInMillis());
        if (offset != 0) {
            int hours = Math.abs((offset / (60 * 1000)) / 60) - 5;
            return bizStartUTC - hours;
        } else {
            return bizStartUTC;
        }

    }

    /**
     * validateBusinessHours
     * Makes sure appointment is scheduled during business hours
     *
     * @param startDateTime start appointment datetime
     * @param endDateTime end appointment datetime
     * @param apptDate appointment date
     *
     * @return Boolean indicating valid input
     */
    public Boolean checkBusinessHours (LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDate apptDate) {

        ZonedDateTime startZonedDateTime = ZonedDateTime.of(startDateTime, currentZone);
        ZonedDateTime endZonedDateTime = ZonedDateTime.of(endDateTime, currentZone);

        ZonedDateTime startBusinessHours = ZonedDateTime.of(apptDate, LocalTime.of(8,0),
                ZoneId.of("America/New_York"));
        ZonedDateTime endBusinessHours = ZonedDateTime.of(apptDate, LocalTime.of(22, 0),
                ZoneId.of("America/New_York"));

        if (startZonedDateTime.isBefore(startBusinessHours) | startZonedDateTime.isAfter(endBusinessHours) |
                endZonedDateTime.isBefore(startBusinessHours) | endZonedDateTime.isAfter(endBusinessHours) |
                startZonedDateTime.isAfter(endZonedDateTime)) {
            return false;

        }
        else {
            return true;
        }

    }

}


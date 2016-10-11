package io.wabm.address.util;

/**
 * Created by MainasuK on 2016-10-11.
 */

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Helper funciton for handling dates.
 */
public class DateUtil {

    /**
     * The date pattern that is used for conversion. Change as you wish.
     */
    private static final String kDatePattern = "yyyy-MM-dd";

    /**
     * The date formatter
     */
    private static final DateTimeFormatter kDateFormatter = DateTimeFormatter.ofPattern(kDatePattern);

    /**
     * Returns the given date as a well formatted String. The above defined
     * {@link DateUtil#kDatePattern} is used.
     *
     * @param date the date to be returned as a string
     * @return formatted string
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }

        return kDateFormatter.format(date);
    }

    /**
     * Converts a String in the format of the defined {@link DateUtil#kDatePattern} to a {@link LocalDate} object.
     *
     * Returns null if the String could not be converted.
     *
     * @param dateString the date as String
     * @return the date object or null if it could not be converted
     */
    public static LocalDate parse(String dateString) {
        try {
            return kDateFormatter.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Checks the String whether it is a valid date
     *
     * @param dateString
     * @return
     */
    public static boolean validDate(String dateString) {
        // Try to parse the String.
        return DateUtil.parse(dateString) != null;
    }

    @Test
    public void testValidDate() {
        assertEquals(true, validDate("1999-02-21"));
    }
}

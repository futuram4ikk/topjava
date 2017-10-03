package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

public class DateTimeConverter {
    public static class DateConverter implements Formatter<LocalDate> {


        @Override
        public LocalDate parse(String text, Locale locale) throws ParseException {
            return DateTimeUtil.parseLocalDate(text);
        }

        @Override
        public String print(LocalDate date, Locale locale) {
            return String.valueOf(date);
        }
    }

    public static class TimeConverter implements Formatter<LocalTime> {

        @Override
        public LocalTime parse(String text, Locale locale) throws ParseException {
           return DateTimeUtil.parseLocalTime(text);
        }

        @Override
        public String print(LocalTime time, Locale locale) {
            return String.valueOf(time);
        }
    }
}

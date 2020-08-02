package com.itrain.common.converter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import com.fasterxml.jackson.databind.util.StdConverter;

public class LocalDateTimeToStringConverter extends StdConverter<LocalDateTime, String> {

    @Override
    public String convert(LocalDateTime value) {
        if (Objects.isNull(value)) {
            return null;
        }
        ZonedDateTime zonedDateTime = value.atZone(ZoneOffset.UTC);
        return zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

}

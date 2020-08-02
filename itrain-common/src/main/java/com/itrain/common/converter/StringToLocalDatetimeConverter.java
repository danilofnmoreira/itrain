package com.itrain.common.converter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.util.StdConverter;

import org.apache.commons.lang3.StringUtils;

public class StringToLocalDatetimeConverter extends StdConverter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        ZonedDateTime zonedDateTime = ZonedDateTime.parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        if (!zonedDateTime.getZone().equals(ZoneOffset.UTC)) {
            return zonedDateTime.toLocalDateTime().atZone(zonedDateTime.getZone()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
        }

        return zonedDateTime.toLocalDateTime();
    }

}

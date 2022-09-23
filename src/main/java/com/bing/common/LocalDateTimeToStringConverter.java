package com.bing.common;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * 废弃
 * 放在 @JsonSerialize 注解内，将时间对象序列化为字串。  @JsonSerialize(using = LocalDateTimeToStringConverter.class)
 *
 * @author: LiBingYan
 * @时间: 2022/9/22
 */
public class LocalDateTimeToStringConverter extends StdConverter<LocalDateTime, String> {
    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

    @Override
    public String convert(LocalDateTime value) {
        return value.format(DATE_FORMATTER);
    }
}
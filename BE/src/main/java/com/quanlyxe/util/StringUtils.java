package com.quanlyxe.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtils {

    public static String trim(String value) {
        return value == null ? null : value.trim();
    }

    public static String trimUpperCase(String value) {
        return value == null ? null : value.trim().toUpperCase();
    }
}

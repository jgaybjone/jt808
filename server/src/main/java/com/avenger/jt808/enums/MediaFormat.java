package com.avenger.jt808.enums;

import java.util.Arrays;

/**
 * Created by jg.wang on 2020/4/13.
 * Description:
 */
public enum MediaFormat {
    JPEG,
    TIF,
    MP3,
    WAV,
    WMV,
    OTHER;

    public static MediaFormat valueOf(byte value) {
        return Arrays.stream(values())
                .filter(v -> v.ordinal() == value)
                .findAny()
                .orElse(OTHER);
    }
}

package com.avenger.jt808.enums;

import java.util.Arrays;

/**
 * Created by jg.wang on 2020/4/13.
 * Description:
 * 0:图像;1:音频;2:视频;
 */
public enum MultimediaEventType {
    PICTURE,
    VIDEO,
    AUDIO;

    public static MultimediaEventType valueOf(byte value) {
        return Arrays.stream(values())
                .filter(v -> v.ordinal() == value)
                .findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(MultimediaEventType.class, "illegal value: " + value));
    }
}

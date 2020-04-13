package com.avenger.jt808.enums;

import lombok.Getter;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
public enum DrivingTimeResult {
    TOO_SHORT((byte) 0x00),

    TOO_LONG((byte) 0x01);

    @Getter
    private byte value;

    DrivingTimeResult(byte value) {
        this.value = value;
    }

    public static DrivingTimeResult valueOf(byte value) {
        if (value == 0x00) {
            return TOO_SHORT;
        } else {
            return TOO_LONG;
        }
    }
}

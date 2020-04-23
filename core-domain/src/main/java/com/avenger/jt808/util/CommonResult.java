package com.avenger.jt808.util;

import lombok.Getter;

/**
 * Created by jg.wang on 2020/4/9.
 * Description:
 */
public enum CommonResult {
    SUCCESS((byte) 0),
    FAIL((byte) 1),
    BAD_MSG((byte) 2),
    NOT_SUPPORT((byte) 3),
    ALARM_CONFIRMATION((byte) 4);

    @Getter
    private byte value;

    CommonResult(byte b) {
        this.value = b;
    }

    public static CommonResult valueOf(byte b) {
        switch (b) {
            case 0:
                return SUCCESS;
            case 1:
                return FAIL;
            case 2:
                return BAD_MSG;
            case 3:
                return NOT_SUPPORT;
            case 4:
                return ALARM_CONFIRMATION;
            default:
                throw new EnumConstantNotPresentException(CommonResult.class, "value: " + b + " is one illegal value!");
        }
    }
}

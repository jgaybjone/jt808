package com.avenger.jt808.enums;

import java.util.Arrays;

/**
 * Created by jg.wang on 2020/4/13.
 * Description:
 */
public enum ShootAtOnceResult {
    SUCCESS,
    FAIL,
    NOT_SUPPORT;

    public static ShootAtOnceResult valueOf(byte va) {
        return Arrays.stream(values())
                .filter(v -> v.ordinal() == va)
                .findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(ShootAtOnceResult.class, "illegal value: " + va));
    }
}

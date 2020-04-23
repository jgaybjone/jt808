package com.avenger.jt808.enums;

import java.util.Arrays;

/**
 * Created by jg.wang on 2020/4/18.
 * Description:
 */
public enum RealTimeDataType {
    AUDIO,
    VIDEO,
    TWO_WAY,
    MONITOR,
    CENTRAL_BROADCASTING,
    TRANSPARENT;
//
//    public static RealTimeDataType valueOf(int v) {
//        return Arrays.stream(values())
//                .filter(i -> i.ordinal() == v)
//                .findAny()
//                .orElseThrow(() -> new EnumConstantNotPresentException(RealTimeDataType.class, "illegal value : " + v));
//    }
}

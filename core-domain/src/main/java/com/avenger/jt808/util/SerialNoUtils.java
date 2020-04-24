package com.avenger.jt808.util;

import lombok.experimental.UtilityClass;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jg.wang on 2020/4/24.
 * Description:
 */
@UtilityClass
public class SerialNoUtils {

    private static final AtomicInteger inc = new AtomicInteger(0);

    public static short next() {
        return (short) inc.incrementAndGet();
    }

}

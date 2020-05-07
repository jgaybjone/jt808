package com.avenger.jt808.util;

import lombok.experimental.UtilityClass;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * Created by jg.wang on 2020/4/24.
 * Description:
 */
@UtilityClass
public class SerialNoUtils {

    private static final AtomicInteger inc = new AtomicInteger(0);

    private static Supplier<Short> supplier = () -> ((short) inc.incrementAndGet());

    public static synchronized void setSupplier(Supplier<Short> supplier) {
        SerialNoUtils.supplier = supplier;
    }

    public static short next() {
        return supplier.get();
    }

}

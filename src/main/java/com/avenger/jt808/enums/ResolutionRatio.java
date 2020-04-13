package com.avenger.jt808.enums;

import java.util.Arrays;

/**
 * Created by jg.wang on 2020/4/13.
 * Description:
 */
public enum ResolutionRatio {
    /**
     * 320×240
     */
    QVGA,
    /**
     * 640×480
     */
    VGA,
    /**
     * 800×600
     */
    SVGA,
    /**
     * 1024×768
     */
    XGA,
    /**
     * 176×144
     */
    QCIF,
    /**
     * 352×288
     */
    CIF,
    /**
     * 704*288
     */
    HALF_D1,
    /**
     * 704*576
     */
    D1;

    public static ResolutionRatio valueOf(byte value) {
        return Arrays.stream(values()).filter(v -> v.ordinal() == (value - 1)).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(ResolutionRatio.class, "illegal value: " + value));
    }

}

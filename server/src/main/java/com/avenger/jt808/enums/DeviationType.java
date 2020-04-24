package com.avenger.jt808.enums;

import com.avenger.jt808.domain.EnumValue;
import lombok.Getter;

/**
 * Created by jg.wang on 2019/12/14.
 * Description:
 */
public enum DeviationType implements EnumValue {
    /**
     * 左侧偏离
     */
    LEFT_DEVIATION((byte) 0x01),
    /**
     * 右侧偏离
     */
    RIGHT_DEVIATION((byte) 0x02),
    ;

    @Getter
    private final byte value;

    DeviationType(byte b) {
        this.value = b;
    }

    public static DeviationType ofValue(Object b) {

        if (b instanceof Byte) {
            final Byte b1 = (Byte) b;

            if (b1 == 0x02) {
                return RIGHT_DEVIATION;
            } else {
                return LEFT_DEVIATION;
            }
        } else {
            return DeviationType.valueOf(b.toString());
        }
    }
}

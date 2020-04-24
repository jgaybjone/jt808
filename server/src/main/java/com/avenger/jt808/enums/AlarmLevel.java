package com.avenger.jt808.enums;

import com.avenger.jt808.domain.EnumValue;
import lombok.Getter;

/**
 * 报警级别
 */
public enum AlarmLevel implements EnumValue {
    /**
     * 0x01:一级报警
     */
    LEVEL_1((byte) 0x01),
    /**
     * 0x02:二级报警
     */
    LEVEL_2((byte) 0x02),
    ;

    @Getter
    private final byte value;

    AlarmLevel(byte b) {
        this.value = b;
    }

    public static AlarmLevel ofValue(Object b) {

        if (b instanceof Byte) {
            final Byte b1 = (Byte) b;

            if (b1 == 0x02) {
                return LEVEL_2;
            } else {
                return LEVEL_1;
            }
        } else {
            return AlarmLevel.valueOf(b.toString());
        }
    }
}

package com.avenger.jt808.enums;

import com.avenger.jt808.domain.EnumValue;
import lombok.Getter;

/**
 * 0x01:限速标志
 * 0x02:限高标志
 * 0x03:限重标志
 * 仅报警类型为 0x06 和 0x10 时有效
 */
public enum RoadSignType implements EnumValue {
    SPEED_LIMIT((byte) 0x01),
    HEIGHT_LIMIT((byte) 0x02),
    WEIGHT_LIMIT((byte) 0x03),
    ;

    @Getter
    private final byte value;

    RoadSignType(byte b) {
        this.value = b;
    }

    public static RoadSignType ofValue(Object b) {
        if (b instanceof Byte) {
            final Byte b1 = (Byte) b;
            switch (b1) {
                case 0x01:
                    return SPEED_LIMIT;
                case 0x02:
                    return HEIGHT_LIMIT;
                default:
                    return WEIGHT_LIMIT;
            }
        } else {
            return RoadSignType.valueOf(b.toString());
        }
    }
}

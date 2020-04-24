package com.avenger.jt808;


import com.avenger.jt808.domain.EnumValue;

/**
 * Created by jg.wang on 2019/12/14.
 * Description:
 */
public enum AlarmFileType implements EnumValue {

    PIC((byte) 0x00),
    AUDIO((byte) 0x01),
    VIDEO((byte) 0x02),
    TXT((byte) 0x03),
    OTHER((byte) 0x04),
    ;

    private final byte value;

    AlarmFileType(byte b) {
        this.value = b;
    }

    @Override
    public byte getValue() {
        return this.value;
    }

    public static AlarmFileType create(byte b) {
        switch (b) {
            case 0x00:
                return PIC;
            case 0x01:
                return AUDIO;
            case 0x02:
                return VIDEO;
            case 0x03:
                return TXT;
            default:
                return OTHER;
        }
    }
}

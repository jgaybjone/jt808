package com.avenger.jt808.enums;

import com.avenger.jt808.domain.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

/**
 * Created by jg.wang on 2019/12/14.
 * Description:
 * 标志状态
 * 0x00:不可用
 * 0x01:开始标志
 * 0x02:结束标志 该字段仅适用于有开始和结束标志类型的报警或事件，
 * 报警类型或事件类型无开始和结束标志，则该位不可 用，填入 0x00 即可。
 */

public enum FlagStatus implements EnumValue {
    /**
     * 不可用
     */
    UNAVAILABLE((byte) 0x00),

    /**
     * 开始标记
     */
    START_TAG((byte) 0x01),

    /**
     * 结束标记
     */
    END_TAG((byte) 0x02);

    @Getter
    private final byte value;

    FlagStatus(byte b) {
        this.value = b;
    }

    @JsonCreator
    public static FlagStatus ofValue(Object b) {
        if (b instanceof Byte) {
            final Byte b1 = (Byte) b;
            switch (b1) {
                case 0x01:
                    return START_TAG;
                case 0x02:
                    return END_TAG;
                default:
                    return UNAVAILABLE;
            }
        } else {
            return FlagStatus.valueOf(b.toString());
        }
    }
}

package com.avenger.jt808.enums;

import lombok.Getter;

/**
 * Created by jg.wang on 2020/4/9.
 * Description:
 */
public enum RegisterResult {
    /**
     * 成功
     */
    OK((byte) 0),
    /**
     * 车辆已经被注册
     */
    ALREADY_REGISTERED((byte) 1),
    /**
     * 系统中无该车辆
     */
    NO_SUCH_VEHICLE((byte) 2),
    /**
     * 终端已被注册
     */
    TERMINAL_REGISTERED((byte) 3),
    /**
     * 系统中无该终端
     */
    NO_SUCH_TERMINAL((byte) 4);

    @Getter
    private byte value;

    RegisterResult(byte value) {
        this.value = value;
    }

    public static RegisterResult valueOf(byte b) {
        switch (b) {
            case 0:
                return OK;
            case 1:
                return ALREADY_REGISTERED;
            case 2:
                return NO_SUCH_VEHICLE;
            case 3:
                return TERMINAL_REGISTERED;
            default:
                return NO_SUCH_TERMINAL;
        }
    }
}

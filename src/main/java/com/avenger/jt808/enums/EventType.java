package com.avenger.jt808.enums;

import lombok.Getter;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
@SuppressWarnings("DuplicatedCode")
public enum EventType {

    DELETE_ALL((byte) 0),
    UPDATE((byte) 1),
    ADD((byte) 2),
    CHANGE((byte) 3),
    DELETE_SOME((byte) 4);


    @Getter
    private byte value;

    EventType(byte value) {
        this.value = value;
    }

    public static EventType valueOf(byte value) {
        switch (value) {
            case 0:
                return DELETE_ALL;
            case 1:
                return UPDATE;
            case 2:
                return ADD;
            case 3:
                return CHANGE;
            default:
                return DELETE_SOME;
        }
    }
}

package com.avenger.jt808.enums;

import lombok.Getter;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
public enum MenuSettingType {

    DELETE_ALL(0),
    UPDATE(1),
    ADD(2),
    CHANGE(3);

    @Getter
    private int value;

    MenuSettingType(int value) {
        this.value = value;
    }

    public static MenuSettingType valueOf(int value) {
        switch (value) {
            case 0:
                return DELETE_ALL;
            case 1:
                return UPDATE;
            case 2:
                return ADD;
            default:
                return CHANGE;
        }
    }
}

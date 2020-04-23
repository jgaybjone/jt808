package com.avenger.jt808.enums;

import lombok.Getter;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
public enum ContactFlag {
    IN(1),
    OUT(2),
    IN_AND_OUT(3);

    @Getter
    private int value;

    ContactFlag(int value) {
        this.value = value;
    }
}

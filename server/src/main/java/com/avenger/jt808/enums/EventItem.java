package com.avenger.jt808.enums;

import java.util.Arrays;

/**
 * Created by jg.wang on 2020/4/13.
 * Description:
 * 0:平台下发指令;1:定时动作;2:抢劫报警触 发;3:碰撞侧翻报警触发;4:门开拍照;5:门 关拍照;6:车门由开变关，时速从<20 公里到超 过 20 公里;7:定距拍照;
 * 其他保留
 */
public enum EventItem {
    /**
     * 0:平台下发指令
     */
    PLATFORM_COMMAND,
    /**
     * 1:定时动作
     */
    TIMING_ACTION,
    /**
     * 2:抢劫报警触发
     */
    ROBBERY_ALARM,

    /**
     * 3:碰撞侧翻报警触发
     */
    COLLISION_ROLLOVER_ALARM,
    /**
     * 4:门开拍照
     */
    DOOR_OPENING_PHOTO_TAKING,
    /**
     * 5:门关拍照
     */
    DOOR_CLOSING_PHOTO_TAKING,
    /**
     * 6:车门由开变关，时速从<20 公里到超 过 20 公里
     */
    DOOR_CHANGING,
    /**
     * 7:定距拍照
     */
    DISTANCE_PHOTO_TAKING,

    OTHER;


    public static EventItem valueOf(byte value) {
        return Arrays.stream(values())
                .filter(v -> v.ordinal() == value)
                .findAny()
                .orElse(OTHER);
    }
}

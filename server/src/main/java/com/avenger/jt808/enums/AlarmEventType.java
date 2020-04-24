package com.avenger.jt808.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * Created by jg.wang on 2019/12/18.
 * Description:
 */
public enum AlarmEventType {

    /**
     * 前向碰撞报警
     */
    FORWARD_COLLISION((short) 0x6401),
    /**
     * 车道偏离报警
     */
    LANE_DEPARTURE((short) 0x6402),
    /**
     * 车距过近报警
     */
    TOO_CLOSE_DISTANCE((short) 0x6403),

    /**
     * 行人碰撞报警
     */
    PEDESTRIAN_COLLISION((short) 0x6404),
    /**
     * 频繁变道报警
     */
    FREQUENT_LANE_CHANGE((short) 0x6405),
    /**
     * 道路标识超限报警
     */
    ROAD_SIGN_OVERRUN((short) 0x6406),
    /**
     * 障碍物报警
     */
    OBSTACLE((short) 0x6407),
    /**
     * 道路标志识别事件
     */
    ROAD_SIGN_RECOGNITION_EVENTS((short) 0x6410),
    /**
     * 主动抓拍事件
     */
    ACTIVE_CAPTURE_EVENT((short) 0x6411),

    UNKNOWN((short) 0x64FF),

    /**
     * 后方接近报警
     */
    REAR_APPROACH((short) 0x6701),
    /**
     * 左侧后方接近报警
     */
    LEFT_REAR_APPROACH((short) 0x6702),
    /**
     * 右侧后方接近报警
     */
    RIGHT_REAR_APPROACH((short) 0x6703),
    /**
     * 疲劳驾驶报警
     */
    FATIGUE_DRIVING((short) 0x6501),
    /**
     * 接电话报警
     */
    ANSWER_THE_PHONE((short) 0x6502),
    /**
     * 抽烟报警
     */
    SMOKING((short) 0x6503),

    /**
     * 分神驾驶报警
     */
    DISTRACTED_DRIVING((short) 0x6504),
    /**
     * 驾驶员异常报警
     */
    DRIVER_ABNORMALITY((short) 0x6505),
    /**
     * 自动抓拍事件
     */
    AUTOMATIC_CAPTURE((short) 0x6510),
    /**
     * 驾驶员变更事件
     */
    DRIVER_CHANGE((short) 0x6511),

    DSM_UNKNOWN((short) 0x65FF);

    @Getter
    private final short value;

    AlarmEventType(short i) {
        this.value = i;
    }

    public static AlarmEventType create(short value) {
        return Arrays.stream(values())
                .filter(v -> v.value == value).findFirst()
                .orElse(null);
    }
}

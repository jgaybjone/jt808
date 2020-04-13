package com.avenger.jt808.enums;

import lombok.Getter;

/**
 * Created by jg.wang on 2020/4/10.
 * Description:
 */
public enum RegionType {
    /**
     * 无固定位置
     */
    NO_SPECIFIC_LOCATION(((byte) 0x00)),
    /**
     * 圆形区域
     */
    CIRCULAR_REGION(((byte) 0x01)),
    /**
     * 矩形区域
     */
    RECTANGULAR_REGION(((byte) 0x02)),
    /**
     * 多边形区域
     */
    POLYGONAL_REGION(((byte) 0x03)),
    /**
     * 路段
     */
    ROAD_SECTION(((byte) 0x04));

    @Getter
    private byte value;

    RegionType(byte i) {
        this.value = i;
    }

    public static RegionType valueOf(byte value) {

        switch (value) {
            case 0x00:
                return NO_SPECIFIC_LOCATION;
            case 0x01:
                return CIRCULAR_REGION;
            case 0x03:
                return RECTANGULAR_REGION;
            case 0x04:
                return POLYGONAL_REGION;
            default:
                return ROAD_SECTION;
        }
    }
}

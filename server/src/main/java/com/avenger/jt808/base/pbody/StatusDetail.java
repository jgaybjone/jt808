package com.avenger.jt808.base.pbody;

import lombok.AllArgsConstructor;

/**
 * Created by jg.wang on 2020/4/10.
 * Description:
 */
@AllArgsConstructor
public class StatusDetail {
    private int status = 0;

    /**
     * ACC 开关
     */
    public boolean isAccOn() {
        return (status & 0b1) > 0;
    }

    /**
     * 已定位
     */
    public boolean isAlreadyPositioned() {
        return (status & 0b10) > 0;
    }

    /**
     * 0: true 北纬;1: false 南纬
     */
    public boolean isNorthLatitude() {
        return (status & 0b100) <= 0;
    }

    /**
     * 0: true 东经;1: false 西经
     */

    public boolean isEastLongitude() {
        return (status & 0b1000) <= 0;
    }

    /**
     * 0:运营状态;1:停运状态
     */

    public boolean isOutageState() {
        return (status & 0b10000) > 0;
    }

    /**
     * 定位加密
     */
    public boolean isLocationEncryption() {
        return (status & 0b100000) > 0;
    }

    /**
     * 00:空车;01:半载;10:保留;11:满载 (可用于客车的空、重车及货车的空载、满载状态表示，人工输入或传感器 获取)
     */
    public LoadStatus getLoadStatus() {
        final boolean b8 = (status & 0b100000000) > 0;
        final boolean b9 = (status & 0b1000000000) > 0;
        if (b8 && b9)
            return LoadStatus.FULL;

        if (!(b8 || b9)) {
            return LoadStatus.EMPTY;
        }

        if (b8)
            return LoadStatus.HALF;

        return LoadStatus.UNDEFINED;
    }

    /**
     * 油路断开
     */
    public boolean isOilCircuitBreak() {
        return (status & 0b10000000000) > 0;
    }

    /**
     * 电路断开
     */
    public boolean isCircuitDisconnection() {
        return (status & 0b100000000000) > 0;
    }

    /**
     * 车门加锁
     */
    public boolean isDoorLock() {
        return (status & 0b1000000000000) > 0;
    }

    /**
     * 前门开
     */
    public boolean isFrontDoorOpen() {
        return (status & 0b10000000000000) > 0;
    }

    /**
     * 中门开
     */
    public boolean isMiddleDoorOpening() {
        return (status & 0b100000000000000) > 0;
    }

    /**
     * 后门开
     */
    public boolean isBackDoorOpening() {
        return (status & 0b1000000000000000) > 0;
    }

    /**
     * 驾驶席门开
     */
    public boolean isDriverDoorOpen() {
        return (status & 0b10000000000000000) > 0;
    }

    /**
     * 第五门开
     */
    public boolean isFifthDoorOpen() {
        return (status & 0b100000000000000000) > 0;
    }

    /**
     * 使用 GPS 卫星进行定位
     */
    public boolean isGpsLocation() {
        return (status & 0b1000000000000000000) > 0;
    }

    /**
     * 使用 北斗进行定位
     */
    public boolean isBeiDouLocation() {
        return (status & 0b10000000000000000000) > 0;
    }

    /**
     * 使用GLONASS进行定位
     */
    public boolean isGLONASSLocation() {
        return (status & 0b100000000000000000000) > 0;
    }

    /**
     * 使用Galileo进行定位
     */
    public boolean isGalileoLocation() {
        return (status & 0b1000000000000000000000) > 0;
    }

    public enum LoadStatus {
        EMPTY, HALF, UNDEFINED, FULL
    }
}

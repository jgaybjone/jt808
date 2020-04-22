package com.avenger.jt808.base.pbody;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by jg.wang on 2020/4/10.
 * Description:
 */
@SuppressWarnings("FieldMayBeFinal")
@AllArgsConstructor
@Getter
public class AlarmDetail {

    private int alarmFlag = 0;

    /**
     * 紧急报警
     */
    public boolean isEmergencyAlarm() {
        return (alarmFlag & 0b1) > 0;
    }

    /**
     * 超速
     */
    public boolean isSpeeding() {
        return (alarmFlag & 0b10) > 0;
    }

    /**
     * 疲劳驾驶
     */
    public boolean isFatigueDriving() {
        return (alarmFlag & 0b100) > 0;
    }

    /**
     * 危险预警
     */
    public boolean isDangerWarning() {
        return (alarmFlag & 0b1000) > 0;
    }

    /**
     * 定位模块异常
     */
    public boolean isLocationFault() {
        return (alarmFlag & 0b10000) > 0;
    }

    /**
     * 定位模块天线断开
     */
    public boolean isAntennaDetachment() {
        return (alarmFlag & 0b100000) > 0;
    }

    /**
     * 定位模块天线短路
     */
    public boolean isAntennaShortCircuit() {
        return (alarmFlag & 0b1000000) > 0;
    }


    /**
     * 终端主电源欠压
     */
    public boolean isTerminalMainPowerSupplyUnderVoltage() {
        return (alarmFlag & 0b10000000) > 0;
    }

    /**
     * 终端主电源掉电
     */
    public boolean isTerminalMainPowerSupplyPowerFailure() {
        return (alarmFlag & 0b100000000) > 0;
    }

    /**
     * 1:终端 LCD 或显示器故障
     */
    public boolean isTerminalLCDFailure() {
        return (alarmFlag & 0b1000000000) > 0;
    }

    /**
     * TTS 模块故障
     */
    public boolean isTTSModuleFailure() {
        return (alarmFlag & 0b10000000000) > 0;
    }

    /**
     * 1:摄像头故障
     */
    public boolean isCameraFault() {
        return (alarmFlag & 0b100000000000) > 0;
    }

    /**
     * 道路运输证 IC 卡模块故障
     */
    public boolean isRoadTransportCertificateICCardModuleFailure() {
        return (alarmFlag & 0b1000000000000) > 0;
    }

    /**
     * 超速预警
     */
    public boolean isSpeedingWarning() {
        return (alarmFlag & 0b10000000000000) > 0;
    }

    /**
     * 疲劳驾驶预警
     */
    public boolean isFatigueDrivingWarning() {
        return (alarmFlag & 0b100000000000000) > 0;
    }

    /**
     * 当天累计驾驶超时
     */
    public boolean isAccumulatedDrivingOvertimeOfTheDay() {
        return (alarmFlag & 0b1000000000000000000) > 0;
    }

    /**
     * 超时停车
     */
    public boolean isOvertimeParking() {
        return (alarmFlag & 0b10000000000000000000) > 0;
    }

    /**
     * 进出区域
     */
    public boolean isImportAndExportArea() {
        return (alarmFlag & 0b100000000000000000000) > 0;
    }

    /**
     * 进出路线
     */
    public boolean isRouteOfEntryAndExit() {
        return (alarmFlag & 0b1000000000000000000000) > 0;
    }

    /**
     * 路段行驶时间不足/过长
     */
    public boolean isInsufficientTooLongDrivingTime() {
        return (alarmFlag & 0b10000000000000000000000) > 0;
    }

    /**
     * 路线偏离报警
     */
    public boolean isRouteDeviationAlarm() {
        return (alarmFlag & 0b100000000000000000000000) > 0;
    }

    /**
     * 车辆 VSS 故障
     */
    public boolean isVehicleVSSFailure() {
        return (alarmFlag & 0b1000000000000000000000000) > 0;
    }

    /**
     * 车辆油量异常
     */
    public boolean isAbnormalVehicleFuelVolume() {
        return (alarmFlag & 0b10000000000000000000000000) > 0;
    }

    /**
     * 车辆被盗(通过车辆防盗器)
     */
    public boolean isVehicleTheft() {
        return (alarmFlag & 0b100000000000000000000000000) > 0;
    }

    /**
     * 车辆非法点火
     */
    public boolean isIllegalVehicleIgnition() {
        return (alarmFlag & 0b1000000000000000000000000000) > 0;
    }

    /**
     * 车辆非法位移
     */
    public boolean isIllegalVehicleDisplacement() {
        return (alarmFlag & 0b10000000000000000000000000000) > 0;
    }

    /**
     * 碰撞预警
     */
    public boolean isCollisionWarning() {
        return (alarmFlag & 0b100000000000000000000000000000) > 0;
    }

    /**
     * 侧翻预警
     */
    public boolean isRolloverWarning() {
        return (alarmFlag & 0b1000000000000000000000000000000) > 0;
    }

    /**
     * 非法开门报警
     */
    public boolean isIllegalDoorOpeningAlarm() {
        return (alarmFlag & 0b10000000000000000000000000000000) < 0;
    }

}

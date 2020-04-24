package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.AdditionalAble;
import com.avenger.jt808.domain.AlarmIdentification;
import com.avenger.jt808.domain.EnumValue;
import com.avenger.jt808.enums.FlagStatus;
import com.avenger.jt808.util.ByteArrayUtils;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.Getter;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/23.
 * Description:
 */
@AdditionalAble(type = 0x66)
@Data
public class TpmsAdd implements Additional {
    /**
     * 报警 ID
     * 按照报警先后，从 0 开始循环累加，不区分报警类型。
     */
    private int alarmId;

    /**
     * 状态标志
     */
    private FlagStatus flagStatus;

    private int speed;

    /**
     * 高程
     */
    private int altitude;

    private long latitude;

    private long longitude;

    private LocalDateTime date;

    /**
     * 车辆状态
     */
    private String vehicleState;

    /**
     * 报警表示号
     */
    private AlarmIdentification alarmIdentification;

    /**
     * 报警事件列表总数
     */
    private byte totalAlarmEvent;

    private List<TirePressureMonitoring> tirePressureMonitoringList;

    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf buffer) {
        this.alarmId = buffer.readInt();
        final byte b = buffer.readByte();
        this.flagStatus = FlagStatus.ofValue(b);
        this.speed = buffer.readUnsignedByte();
        this.altitude = buffer.readUnsignedShort();
        this.latitude = buffer.readInt();
        this.longitude = buffer.readInt();
        this.date = ByteArrayUtils.bcdToDate(buffer, 6);
        this.vehicleState = Integer.toBinaryString((buffer.readShort() & 0xFFFF) + 0x10000).substring(1);
        final AlarmIdentification alarmIdentification = new AlarmIdentification();
        this.alarmIdentification = alarmIdentification;
        final String terId = ByteBufUtils.toStringWithGBK(buffer, 7);
        alarmIdentification.setTerminalId(terId);
        alarmIdentification.setDate(ByteArrayUtils.bcdToDate(buffer, 6));
        alarmIdentification.setSerialNumber(buffer.readByte());
        alarmIdentification.setNumberOfAttachments(buffer.readByte());
        alarmIdentification.setReserve(buffer.readByte());
        this.totalAlarmEvent = buffer.readByte();
        final ArrayList<TirePressureMonitoring> list = new ArrayList<>();
        for (int i = 0; i < totalAlarmEvent; i++) {
            final TirePressureMonitoring monitoring = new TirePressureMonitoring();
            monitoring.setTirePosition(TirePosition.ofValue(buffer.readByte()));
            monitoring.setAlarmType(Integer.toBinaryString((buffer.readShort() & 0xFFFF) + 0x10000).substring(1));
            monitoring.setTirePressure(buffer.readByte());
            monitoring.setTireTemperature(buffer.readByte());
            monitoring.setBatteryLevel(buffer.readByte());
            list.add(monitoring);
        }
        this.tirePressureMonitoringList = list;
    }

    @Data
    public static class TirePressureMonitoring {
        /**
         * 轮胎位置
         * 报警轮胎位置编号
         * (从左前轮开始以 Z 字形从 00 依次编号，编号与是 否安装 TPMS 无关)
         */
        private TirePosition tirePosition;
        /**
         * 0 表示无报警，1 表示有报警
         * bit0:胎压(定时上报)
         * bit1:胎压过高报警
         * bit2:胎压过低报警
         * bit3:胎温过高报警
         * bit4:传感器异常报警
         * bit5:胎压不平衡报警
         * bit6:慢漏气报警
         * bit7:电池电量低报警
         * bit8~bit15:自定义
         */
        private String alarmType;

        /**
         * 胎压
         */
        private short tirePressure;

        /**
         * 胎温
         */
        private short tireTemperature;

        /**
         * 电池电量
         */
        private short batteryLevel;


    }

    public enum TirePosition implements EnumValue {
        LEFT_FRONT((byte) 0x00),
        RIGHT_FRONT((byte) 0x01),
        LEFT_REAR((byte) 0x02),
        RIGHT_REAR((byte) 0x03),

        ;

        @Getter
        private final byte value;

        TirePosition(byte b) {
            this.value = b;
        }

        public static TirePosition ofValue(byte b) {
            switch (b) {
                case 0x00:
                    return LEFT_FRONT;
                case 0x01:
                    return RIGHT_FRONT;
                case 0x02:
                    return LEFT_REAR;
                default:
                    return RIGHT_REAR;
            }
        }
    }
}

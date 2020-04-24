package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.AdditionalAble;
import com.avenger.jt808.domain.AlarmIdentification;
import com.avenger.jt808.enums.*;
import com.avenger.jt808.util.ByteArrayUtils;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

/**
 * Created by jg.wang on 2020/4/23.
 * Description: 高级驾驶辅助系统报警信息
 */
@AdditionalAble(type = 0x64)
@Data
public class AdasAdd implements Additional {

    /**
     * 报警 ID
     * 按照报警先后，从 0 开始循环累加，不区分报警类型。
     */
    private Integer alarmId;

    private FlagStatus flagStatus;

    private AlarmEventType alarmEventType;

    private AlarmLevel alarmLevel;

    private int speedOfFrontVehicle;

    /**
     * 前车/行人距离
     */
    private int frontVehicleDistance;

    /**
     * 偏离类型
     */
    private DeviationType deviationType;

    /**
     * 道路标志识别类型
     */
    private RoadSignType roadSignType;

    /**
     * 道路标志识别数据
     */
    private int roadSignData;

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

    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.alarmId = byteBuf.readInt();
        this.flagStatus = FlagStatus.ofValue(byteBuf.readByte());
        this.alarmEventType = AlarmEventType.create(Unpooled.buffer(2).writeBytes(new byte[]{(byte) 0x64, byteBuf.readByte()}).readShort());
        this.alarmLevel = AlarmLevel.ofValue(byteBuf.readByte());
        this.speedOfFrontVehicle = byteBuf.readUnsignedByte();
        this.frontVehicleDistance = byteBuf.readUnsignedByte();
        this.deviationType = DeviationType.ofValue(byteBuf.readByte());
        this.roadSignType = RoadSignType.ofValue(byteBuf.readByte());
        this.roadSignData = byteBuf.readUnsignedByte();
        this.speed = byteBuf.readUnsignedByte();
        this.altitude = byteBuf.readUnsignedByte();
        this.latitude = byteBuf.readInt();
        this.longitude = byteBuf.readInt();
        this.date = ByteArrayUtils.bcdToDate(byteBuf, 6);
        this.vehicleState = Integer.toBinaryString((byteBuf.readShort() & 0xFFFF) + 0x10000).substring(1);
        final AlarmIdentification alarmIdentification = new AlarmIdentification();
        this.alarmIdentification = alarmIdentification;
        final String terId = ByteBufUtils.toStringWithGBK(byteBuf, 7);
        alarmIdentification.setTerminalId(terId);
        alarmIdentification.setDate(ByteArrayUtils.bcdToDate(byteBuf, 6));
        alarmIdentification.setSerialNumber(byteBuf.readByte());
        alarmIdentification.setNumberOfAttachments(byteBuf.readByte());
        alarmIdentification.setReserve(byteBuf.readByte());
    }
}

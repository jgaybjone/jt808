package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.AdditionalAble;
import com.avenger.jt808.domain.AlarmIdentification;
import com.avenger.jt808.enums.AlarmEventType;
import com.avenger.jt808.enums.FlagStatus;
import com.avenger.jt808.util.ByteArrayUtils;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

/**
 * Created by jg.wang on 2020/4/23.
 * Description:
 */
@AdditionalAble(type = 0x67)
@Data
public class BsdAdd implements Additional {
    /**
     * 报警 ID
     * 按照报警先后，从 0 开始循环累加，不区分报警类型。
     */
    private int alarmId;

    /**
     * 状态标志
     */
    private FlagStatus flagStatus;

    /**
     * 报警/事件类型
     */
    private AlarmEventType alarmEventType;

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
    public void deSerialize(ByteBuf buffer) {
        this.alarmId = buffer.readInt();
        final byte b = buffer.readByte();
        this.flagStatus = FlagStatus.ofValue(b);
        this.alarmEventType = AlarmEventType.create(Unpooled.buffer(2).writeBytes(new byte[]{(byte) 0x67, buffer.readByte()}).readShort());
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
    }
}

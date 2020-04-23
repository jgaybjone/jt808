package com.avenger.jt808.base.pbody;

import com.avenger.jt808.base.MessageFactory;
import com.avenger.jt808.base.annotation.ReadingMessageType;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.util.ApplicationContextUtils;
import com.avenger.jt808.util.ByteArrayUtils;
import com.avenger.jt808.util.DateUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/10.
 * Description: 位置信息汇报消息体由位置基本信息和位置附加信息项列表组成
 */
@Data
@ReadingMessageType(type = 0x0200)
public class LocationAndAlarmMsg implements Body {
    /**
     * 报警标志
     */
    private int alarmFlag;

    /**
     * 状态位
     */
    private int status = 0;

    /**
     * 以度为单位的纬度值乘以10的6次方，精确到百万 分之一度
     */
    private int latitude;

    /**
     * 以度为单位的经度值乘以10的6次方，精确到百万 分之一度
     */
    private int longitude;

    /**
     * 海拔高度，单位为米(m)
     */
    private short altitude;
    /**
     * 1/10km/h
     */
    private short speed;
    /**
     * 0-359，正北为 0，顺时针
     */
    private short direction;

    /**
     * YY-MM-DD-hh-mm-ss
     * (GMT+8 时间，本标准中之后涉 及的时间均采用此时区)
     */
    private Date time;

    private AlarmDetail alarmDetail;

    private StatusDetail statusDetail;

    private List<Additional> additionals;

    public void setAlarmFlag(int alarmFlag) {
        this.alarmFlag = alarmFlag;
        this.alarmDetail = new AlarmDetail(alarmFlag);
    }

    public void setStatus(int status) {
        this.status = status;
        this.statusDetail = new StatusDetail(status);
    }

    @Override
    public byte[] serialize() {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.setAlarmFlag(byteBuf.readInt());
        this.setStatus(byteBuf.readInt());
        this.latitude = byteBuf.readInt();
        this.longitude = byteBuf.readInt();
        this.altitude = byteBuf.readShort();
        this.speed = byteBuf.readShort();
        this.direction = byteBuf.readShort();
        this.time = DateUtil.stringToDatetime("20" + ByteArrayUtils.toBcdString(byteBuf, 6), "yyyyMMddHHmmss");
        this.additionals = ApplicationContextUtils.getBean(MessageFactory.class).parse(byteBuf);
    }
}

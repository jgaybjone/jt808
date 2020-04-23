package com.avenger.jt808.base.pbody;

import com.avenger.jt808.base.annotation.ReadingMessageType;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.enums.IcReadResult;
import com.avenger.jt808.util.ByteArrayUtils;
import com.avenger.jt808.util.ByteBufUtils;
import com.avenger.jt808.util.DateUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by jg.wang on 2020/4/12.
 * Description:
 */
@ReadingMessageType(type = 0x0702)
@Data
public class DriverIdentityInformationReportingMsg implements Body {

    /**
     * 0x01:从业资格证 IC 卡插入(驾驶员上班);
     * 0x02:从业资格证 IC 卡拔出(驾驶员下班)。
     */
    private byte status;
    /**
     * 插卡/拔卡时间，YY-MM-DD-hh-mm-ss;
     */
    private Date date;

    /**
     * IC 卡读取结果
     */
    private IcReadResult icReadResult;

    /**
     * 驾驶员姓名
     */
    private String driverName;

    /**
     * 从业资格证编码
     */
    private String qualificationNo;

    /**
     * 发证机构名称
     */
    private String institution;

    /**
     * 证件有效期
     */
    private Date periodValidity;

    public boolean hasIcCard() {
        return status == 0x01;
    }

    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.status = byteBuf.readByte();
        this.date = DateUtil.stringToDatetime("20" + ByteArrayUtils.toBcdString(byteBuf, 6), "yyyyMMddHHmmss");
        this.icReadResult = IcReadResult.valueOf(byteBuf.readByte());
        this.driverName = ByteBufUtils.toStringWithGBK(byteBuf, byteBuf.readByte());
        this.qualificationNo = ByteBufUtils.toStringWithGBK(byteBuf, 20).trim();
        this.institution = ByteBufUtils.toStringWithGBK(byteBuf, byteBuf.readByte());
        this.periodValidity = DateUtil.stringToDatetime(ByteArrayUtils.toBcdString(byteBuf, 4), "yyyyMMdd");
    }
}

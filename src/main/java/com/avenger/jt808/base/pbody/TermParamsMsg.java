package com.avenger.jt808.base.pbody;

import com.avenger.jt808.annotation.ReadingMessageType;
import com.avenger.jt808.base.Body;
import com.avenger.jt808.util.ByteArrayUtils;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by jg.wang on 2020/4/10.
 * Description:
 */
@ReadingMessageType(type = 0x0107)
@Data
public class TermParamsMsg implements Body {

    /**
     * bit0，0:不适用客运车辆，1:适用客运车辆;
     * bit1，0:不适用危险品车辆，1:适用危险品车辆;
     * bit2，0:不适用普通货运车辆，1:适用普通货运车辆;
     * bit3，0:不适用出租车辆，1:适用出租车辆;
     * bit6，0:不支持硬盘录像，1:支持硬盘录像;
     * bit7，0:一体机，1:分体机。
     */
    private short termType;

    /**
     * 5 个字节，终端制造商编码。
     */
    private String manufId;

    /**
     * 终端型号
     */
    private String terminalModel;
    /**
     * 终端ID
     */
    private String terminalId;

    private String simNo;

    /**
     * 硬件版本
     */
    private String hardVersion;

    /**
     * 固件版本
     */
    private String frameworkVersion;

    /**
     * GNSS 模块属性
     * bit0，0:不支持 GPS 定位， 1:支持 GPS 定位;
     * bit1，0:不支持北斗定位， 1:支持北斗定位;
     * bit2，0:不支持 GLONASS 定位， 1:支持 GLONASS 定位;
     * bit3，0:不支持 Galileo 定位， 1:支持 Galileo 定位。
     */
    private byte gnssProperties;

    /**
     * 通信模块属性
     * bit0，0:不支持GPRS通信， 1:支持GPRS通信;
     * bit1，0:不支持CDMA通信， 1:支持CDMA通信;
     * bit2，0:不支持TD-SCDMA通信， 1:支持TD-SCDMA通信;
     * bit3，0:不支持WCDMA通信， 1:支持WCDMA通信;
     * bit4，0:不支持CDMA2000通信， 1:支持CDMA2000通信。
     * bit5，0:不支持TD-LTE通信， 1:支持TD-LTE通信;
     * bit7，0:不支持其他通信方式， 1:支持其他通信方式。
     */
    private byte cellularProperties;


    @Override
    public byte[] serialize() {
        final ByteBuf buffer = Unpooled.buffer(200);
        buffer.writeShort(this.termType);
        buffer.writeBytes(this.manufId.getBytes(Charset.forName("GBK")));
        final byte[] bytes1 = new byte[20];
        Arrays.fill(bytes1, (byte) 0);
        final byte[] gbks = terminalModel.getBytes(Charset.forName("GBK"));
        System.arraycopy(gbks, 0, bytes1, 0, Math.min(bytes1.length, gbks.length));
        buffer.writeBytes(bytes1);
        final byte[] bytes2 = new byte[7];
        Arrays.fill(bytes2, ((byte) 0));
        final byte[] gbks1 = terminalId.getBytes(Charset.forName("GBK"));
        System.arraycopy(gbks1, 0, bytes2, 0, Math.min(bytes2.length, gbks1.length));
        buffer.writeBytes(bytes2);
        final byte[] bytes3 = new byte[10];
        Arrays.fill(bytes3, ((byte) 0));
        final byte[] gbks2 = simNo.getBytes(Charset.forName("GBK"));
        System.arraycopy(gbks2, 0, bytes3, 0, Math.min(bytes3.length, gbks2.length));
        buffer.writeBytes(bytes3);
        final byte[] gbks3 = hardVersion.getBytes(Charset.forName("GBK"));
        buffer.writeByte(gbks3.length);
        buffer.writeBytes(gbks3);
        final byte[] gbks4 = frameworkVersion.getBytes(Charset.forName("GBK"));
        buffer.writeByte(gbks4.length);
        buffer.writeBytes(gbks4);
        buffer.writeByte(gnssProperties);
        buffer.writeByte(cellularProperties);
        return buffer.array();

    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.termType = byteBuf.readShort();
        this.manufId = ByteBufUtils.toStringWithGBK(byteBuf, 5);
        this.terminalModel = ByteBufUtils.toStringWithGBK(byteBuf, 20);
        this.terminalId = ByteBufUtils.toStringWithGBK(byteBuf, 7);
        final byte[] bytes = new byte[10];
        byteBuf.readBytes(bytes);
        this.simNo = ByteArrayUtils.toBcdString(bytes);
        this.hardVersion = ByteBufUtils.toStringWithGBK(byteBuf, byteBuf.readByte());
        this.frameworkVersion = ByteBufUtils.toStringWithGBK(byteBuf, byteBuf.readByte());
        this.gnssProperties = byteBuf.readByte();
        this.cellularProperties = byteBuf.readByte();
    }
}

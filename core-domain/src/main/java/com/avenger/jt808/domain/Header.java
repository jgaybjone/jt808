package com.avenger.jt808.domain;

import com.avenger.jt808.util.BinaryUtils;
import com.avenger.jt808.util.ByteArrayUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;

/**
 * Created by jg.wang on 2020/4/9.
 * Description:
 */
@Getter
public class Header {
    /**
     * 消息类型
     */
    private short id;

    /**
     * 消息体属性
     */
    private short msgInf;

    /**
     * 终端手机号
     */
    private String simNo;

    /**
     * 消息流水号
     */
    private short serialNo;

    /**
     * 消息包数量
     */
    private short packetsCount;

    /**
     * 消息包序号
     */
    private short packetNumber;

    private boolean packet;

    private EncryptionForm encryptionForm;

    public int getBodySize() {
        return msgInf & 0x03FF;
    }

    public Header(ByteBuf buffer) {
        id = buffer.readShort();
        msgInf = buffer.readShort();
        final byte[] sim = new byte[6];
        buffer.readBytes(sim);
        this.simNo = ByteArrayUtils.toBcdString(sim);
        this.serialNo = buffer.readShort();
        this.packet = BinaryUtils.bit(msgInf, 14);
        if (packet) {
            this.packetsCount = buffer.readShort();
            this.packetNumber = buffer.readShort();
        } else {
            this.packetsCount = 0;
            this.packetNumber = 0;
        }
        if (BinaryUtils.bit(msgInf, 11)) {
            this.encryptionForm = EncryptionForm.RSA;
        } else {
            this.encryptionForm = EncryptionForm.NOTHING;
        }
    }

    public Header(short id, String simNo, short serialNo, boolean packet, EncryptionForm encryptionForm) {
        this.id = id;
        this.simNo = simNo;
        this.serialNo = serialNo;
        this.msgInf = 0;
        if (packet) {
            msgInf |= 0x2000;
        } else {
            msgInf &= 0xDFFF;
        }
        if (encryptionForm == EncryptionForm.RSA) {
            msgInf |= 0x400;
        } else {
            msgInf &= 0xE3FF;
        }
    }

    public byte[] getRaw(byte len) {
        this.msgInf = ((short) (msgInf | (len & 0X3FF)));

        final ByteBuf byteBuf = Unpooled.buffer(11);
        byteBuf.writeShort(id)
                .writeShort(msgInf)
                .writeBytes(ByteArrayUtils.bcdStrToBytes(simNo))
                .writeShort(serialNo);
        if (packet) {
            byteBuf.writeShort(packetsCount)
                    .writeShort(packetNumber);
        }
        return byteBuf.array();
    }

}

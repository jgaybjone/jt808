package com.avenger.jt808.base.tbody;

import com.avenger.jt808.base.pbody.ControlParam;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.nio.charset.Charset;

/**
 * Created by jg.wang on 2020/4/10.
 * Description: 终端控制8105
 */
@WritingMessageType(type = 0x8105 - 0x10000)
@Data
public class TerminalControlMsg implements Body {

    private byte command;

    private ControlParam controlParam;

    @Override
    public byte[] serialize() {
        final ByteBuf buffer = Unpooled.buffer(200);
        buffer.writeByte(command);
        if (command > 0 && command < 3) {
            buffer.writeByte(controlParam.getConType())
                    .writeBytes(controlParam.getApn().getBytes(Charset.forName("GBK")))
                    .writeByte(';')
                    .writeBytes(controlParam.getUsername().getBytes(Charset.forName("GBK")))
                    .writeByte(';')
                    .writeBytes(controlParam.getPassword().getBytes(Charset.forName("GBK")))
                    .writeByte(';')
                    .writeBytes(controlParam.getIp().getBytes(Charset.forName("GBK")))
                    .writeByte(';')
                    .writeShort(controlParam.getTcpPort())
                    .writeByte(';')
                    .writeShort(controlParam.getUdpPort());
            final byte[] gbks = controlParam.getManufId().getBytes(Charset.forName("GBK"));
            for (int i = 0; i < (Math.min(gbks.length, 5)); i++) {
                buffer.writeByte(gbks[i]);
            }
            buffer
                    .writeBytes(controlParam.getSupervisionPlatAuthCode().getBytes(Charset.forName("GBK")))
                    .writeBytes(controlParam.getHardVersion().getBytes(Charset.forName("GBK")))
                    .writeBytes(controlParam.getFrameworkVersion().getBytes(Charset.forName("GBK")))
                    .writeBytes(controlParam.getUrl().getBytes(Charset.forName("GBK")))
                    .writeShort(controlParam.getConnTime());
        }
        return ByteBufUtils.array(buffer);
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.command = byteBuf.readByte();
        if (command > 0 && command > 3) {
            final ControlParam controlParam = new ControlParam();
            controlParam.setApn(new String(ByteBufUtils.array(this.readBreak(byteBuf)), Charset.forName("GBK")));
            controlParam.setUsername(new String(ByteBufUtils.array(this.readBreak(byteBuf)), Charset.forName("GBK")));
            controlParam.setPassword(new String(ByteBufUtils.array(this.readBreak(byteBuf)), Charset.forName("GBK")));
            controlParam.setIp(new String(ByteBufUtils.array(this.readBreak(byteBuf)), Charset.forName("GBK")));
            controlParam.setTcpPort(this.readBreak(byteBuf).readShort());
            controlParam.setUdpPort(this.readBreak(byteBuf).readShort());
            controlParam.setManufId(new String(ByteBufUtils.array(this.readBreak(byteBuf)), Charset.forName("GBK")));
            controlParam.setSupervisionPlatAuthCode(new String(ByteBufUtils.array(this.readBreak(byteBuf)), Charset.forName("GBK")));
            controlParam.setHardVersion(new String(ByteBufUtils.array(this.readBreak(byteBuf)), Charset.forName("GBK")));
            controlParam.setFrameworkVersion(new String(ByteBufUtils.array(this.readBreak(byteBuf)), Charset.forName("GBK")));
            controlParam.setUrl(new String(ByteBufUtils.array(this.readBreak(byteBuf)), Charset.forName("GBK")));
            controlParam.setConnTime(this.readBreak(byteBuf).readShort());
            this.controlParam = controlParam;

        }
    }

    private ByteBuf readBreak(ByteBuf byteBuf) {
        final ByteBuf buffer = Unpooled.buffer(byteBuf.readableBytes());
        while (byteBuf.isReadable()) {
            final byte b = byteBuf.readByte();
            if (b == ';') {
                break;
            }
            buffer.writeByte(b);
        }
        return buffer;
    }
}


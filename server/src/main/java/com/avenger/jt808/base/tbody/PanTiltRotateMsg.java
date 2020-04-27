package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.io.UnsupportedEncodingException;

/**
 * Created by jg.wang on 2020/4/22.
 * Description: 云台旋转命令
 */
@WritingMessageType(type = ((byte) 0x9301))
@Data
public class PanTiltRotateMsg implements Body {

    private byte channelId;

    private Direction direction;

    private byte speed;

    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return ByteBufUtils.array(Unpooled.buffer(3)
                .writeByte(channelId)
                .writeByte(direction.ordinal())
                .writeByte(speed));
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }

    public enum Direction {
        STOP,
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

}

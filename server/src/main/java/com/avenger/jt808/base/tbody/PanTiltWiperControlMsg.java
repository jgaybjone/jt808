package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.domain.Body;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.io.UnsupportedEncodingException;

/**
 * Created by jg.wang on 2020/4/22.
 * Description: 云台雨刷控制
 */
@WritingMessageType(type = ((byte) 0x9304))
@Data
public class PanTiltWiperControlMsg implements Body {

    private byte channelId;

    private StartStopSign startStopSign;

    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return new byte[]{channelId, ((byte) startStopSign.ordinal())};
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }

    public enum StartStopSign {
        STOP,
        START;
    }
}

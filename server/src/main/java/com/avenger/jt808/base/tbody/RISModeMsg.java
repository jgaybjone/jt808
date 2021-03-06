package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.io.UnsupportedEncodingException;

/**
 * Created by jg.wang on 2020/4/22.
 * Description:
 */
@WritingMessageType(type = (0x9303 - 0x10000))
@Data
public class RISModeMsg implements Body {

    private byte channelId;

    private PanTiltFocusAdjustmentMsg.FocusDirection focusDirection;


    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return new byte[]{channelId, ((byte) focusDirection.ordinal())};
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}

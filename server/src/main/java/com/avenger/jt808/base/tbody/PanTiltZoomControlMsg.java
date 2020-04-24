package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.domain.Body;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.io.UnsupportedEncodingException;

/**
 * Created by jg.wang on 2020/4/22.
 * Description: 云台变倍控制
 */
@WritingMessageType(type = ((byte) 0x9306))
@Data
public class PanTiltZoomControlMsg implements Body {

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

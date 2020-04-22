package com.avenger.jt808.base.tbody;

import com.avenger.jt808.annotation.WritingMessageType;
import com.avenger.jt808.base.Body;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.io.UnsupportedEncodingException;

/**
 * Created by jg.wang on 2020/4/22.
 * Description: 云台焦距调整
 */
@WritingMessageType(type = ((byte) 0x9302))
@Data
public class PanTiltFocusAdjustmentMsg implements Body {

    private byte channelId;

    private FocusDirection focusDirection;


    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return new byte[]{channelId, ((byte) focusDirection.ordinal())};
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }

    public enum FocusDirection {
        ZOOM_IN,
        ZOOM_DOWN,
    }
}

package com.avenger.jt808.base.tbody;

import com.avenger.jt808.annotation.WritingMessageType;
import com.avenger.jt808.base.Body;
import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/18.
 * Description: 实时音视频状态通知
 */
@WritingMessageType(type = ((byte) 0x9105))
@Data
public class RealTimeAudioAndVideoStatusMsg implements Body {

    /**
     * 通道号
     */
    private byte channelId;

    /**
     * 丢包率
     */
    private byte lossRate;

    @Override
    public byte[] serialize() {
        return new byte[]{channelId, lossRate};
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}

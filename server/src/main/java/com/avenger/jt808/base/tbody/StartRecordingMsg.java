package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/13.
 * Description: 录音开始命令
 */
@WritingMessageType(type = (0x8804 - 0x10000))
@Data
public class StartRecordingMsg implements Body {
    /**
     * 录音命令
     */
    private boolean started;
    /**
     * 录音时间
     */
    private short recordingTime;
    /**
     * 保存标志
     */
    private boolean realTime;
    /**
     * 音频采样率
     */
    private byte audioSampling;

    @Override
    public byte[] serialize() {
        return ByteBufUtils.array(Unpooled.buffer(5)
                .writeByte(started ? 1 : 0)
                .writeShort(recordingTime)
                .writeByte(realTime ? 0 : 1)
                .writeByte(audioSampling));
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}

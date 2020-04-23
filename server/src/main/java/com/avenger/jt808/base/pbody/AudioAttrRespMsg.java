package com.avenger.jt808.base.pbody;

import com.avenger.jt808.base.annotation.ReadingMessageType;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.enums.AudioCodeType;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.io.UnsupportedEncodingException;

/**
 * Created by jg.wang on 2020/4/13.
 * Description: 终端上传音视频属性
 */
@ReadingMessageType(type = 0x1003)
@Data
public class AudioAttrRespMsg implements Body {
    /**
     * 音频编码方式
     */
    private AudioCodeType type;
    /**
     * 输入音频声道数
     */
    private byte inputAudioR;
    /**
     * 输入音频采样率
     */
    private byte inputAudioB;
    /**
     * 音频帧长度
     */
    private short audioLen;
    /**
     * 是否支持音频输出
     */
    private boolean audioOutput;
    /**
     * 视频编码方式
     */
    private byte videoCoding;
    /**
     * 支持最大音频通道数量
     */
    private byte maxAudioChannel;
    /**
     * 支持最大视频通道数量
     */
    private byte maxVideoChannel;

    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.type = AudioCodeType.valueOf(byteBuf.readByte());
        this.inputAudioR = byteBuf.readByte();
        this.inputAudioB = byteBuf.readByte();
        this.audioLen = byteBuf.readShort();
        this.audioOutput = byteBuf.readByte() == 1;
        this.videoCoding = byteBuf.readByte();
        this.maxAudioChannel = byteBuf.readByte();
        this.maxVideoChannel = byteBuf.readByte();
    }
}

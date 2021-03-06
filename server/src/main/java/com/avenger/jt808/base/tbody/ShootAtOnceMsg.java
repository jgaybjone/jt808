package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.enums.ResolutionRatio;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/13.
 * Description: 摄像头立即拍摄命令
 */
@WritingMessageType(type = (0x8801 - 0x10000), needReply = true)
@Data
public class ShootAtOnceMsg implements Body {
    /**
     * 通道 ID
     */
    private byte channelId;

    /**
     * 0 表示停止拍摄;
     * 0xFFFF 表示录像;
     * 其它表示拍 照张数
     */
    private short number;

    /**
     * 秒，0 表示按最小间隔拍照或一直录像
     */
    private short shootingTime;

    /**
     * 1:保存;
     * 0:实时上传
     */
    private boolean realTimeUpload;

    /**
     * 分辨率
     */
    private ResolutionRatio resolutionRatio;

    /**
     * 1-10，1 代表质量损失最小，10 表示压缩比最大
     */
    private byte videoQuality;
    /**
     * 亮度
     */
    private byte brightness;
    /**
     * 对比度 0-127
     */
    private byte contrastRatio;
    /**
     * 饱和度 0-127
     */
    private byte saturation;
    /**
     * 色度 0-255
     */
    private byte chroma;

    @Override
    public byte[] serialize() {
        return ByteBufUtils.array(Unpooled.buffer(12)
                .writeByte(channelId)
                .writeShort(number)
                .writeShort(shootingTime)
                .writeByte(realTimeUpload ? 0 : 1)
                .writeByte(resolutionRatio.ordinal() + 1)
                .writeByte(videoQuality)
                .writeByte(brightness)
                .writeByte(contrastRatio)
                .writeByte(saturation)
                .writeByte(chroma));
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}

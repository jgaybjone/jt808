package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.AdditionalAble;
import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

/**
 * Created by jg.wang on 2020/4/17.
 * Description: 视频相关报警
 */
@AdditionalAble(type = 0x14)
public class VideoAdd implements Additional {

    private int raw;


    public void setLossOfVideoSignal(boolean b) {
        if (b) {
            raw = raw | 0b1;
        } else {
            raw = raw & 0xFFFE;
        }
    }

    /**
     * 视频信号丢失
     *
     * @return 丢失 true
     */
    public boolean isLossOfVideoSignal() {
        return (raw & 0b1) > 0;
    }

    public void setVideoSignalOcclusion(boolean b) {
        if (b) {
            raw = raw | 0b10;
        } else {
            raw = raw & (~0b10);
        }
    }

    /**
     * 视频信号遮挡
     *
     * @return 遮挡 true
     */
    public boolean isVideoSignalOcclusion() {
        return (raw & 0b10) > 0;
    }

    public void setMemoryFailure(boolean b) {
        if (b) {
            raw = raw | 0b100;
        } else {
            raw = raw & (~0b100);
        }
    }

    /**
     * 存储单元故障
     *
     * @return 故障 true
     */
    public boolean isMemoryFailure() {
        return (raw & 0b100) > 0;
    }

    public void setOtherEquipmentFailure(boolean b) {
        if (b) {
            raw = raw | 0b1000;
        } else {
            raw = raw & (~0b1000);
        }
    }

    /**
     * 其他视频设备报警
     *
     * @return 报警 true
     */
    public boolean isOtherEquipmentFailure() {
        return (raw & 0b1000) > 0;
    }

    public void setOvercrowding(boolean b) {
        if (b) {
            raw = raw | 0b10000;
        } else {
            raw = raw & (~0b10000);
        }
    }

    /**
     * 客车超员报警
     *
     * @return 超员 true
     */
    public boolean isOvercrowding() {
        return (raw & 0b10000) > 0;
    }

    public void settAbnormalDrivingBehavior(Boolean b) {
        if (b) {
            raw = raw | 0b100000;
        } else {
            raw = raw & (~0b100000);
        }
    }

    /**
     * 异常驾驶行为报警
     *
     * @return 有 true
     */
    public boolean isAbnormalDrivingBehavior() {
        return (raw & 0b100000) > 0;
    }

    public void setSpecialAlarmVideoStorageArrivalThreshold(boolean b) {
        if (b) {
            raw = raw | 0b1000000;
        } else {
            raw = raw & (~0b1000000);
        }
    }

    /**
     * 特殊报警录像存储到达阀值报警
     *
     * @return 到达阀值 true
     */
    public boolean isSpecialAlarmVideoStorageArrivalThreshold() {
        return (raw & 0b1000000) > 0;
    }


    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.raw = byteBuf.readInt();
    }
}

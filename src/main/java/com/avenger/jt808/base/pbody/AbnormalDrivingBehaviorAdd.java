package com.avenger.jt808.base.pbody;

import com.avenger.jt808.annotation.AdditionalAble;
import com.avenger.jt808.util.BinaryUtils;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/17.
 * Description:  异常驾驶行为报警状态
 */
@AdditionalAble(type = 0x18)
@Data
public class AbnormalDrivingBehaviorAdd implements Additional {

    /**
     * 异常驾驶行为类型
     */
    private short abnormalDriveBehaviourType;

    /**
     * 疲劳程度
     */
    private byte tiredLevel;

    private List<String> getAbnormalDriveBehaviour() {
        final List<String> behaviors = new ArrayList<>();
        if (BinaryUtils.bit(abnormalDriveBehaviourType, 0)) {
            behaviors.add("疲劳驾驶");
        }
        if (BinaryUtils.bit(abnormalDriveBehaviourType, 1)) {
            behaviors.add("打电话");
        }
        if (BinaryUtils.bit(abnormalDriveBehaviourType, 2)) {
            behaviors.add("抽烟");
        }
        return behaviors;
    }

    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.abnormalDriveBehaviourType = byteBuf.readShort();
        if (byteBuf.isReadable()) {
            this.tiredLevel = byteBuf.readByte();
        }
    }
}

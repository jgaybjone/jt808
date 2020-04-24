package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.ReadingMessageType;
import com.avenger.jt808.domain.Body;
import io.netty.buffer.ByteBuf;

/**
 * Created by jg.wang on 2020/4/9.
 * Description: 终端心跳消息体
 */
@ReadingMessageType(type = 0x0002)
public class HeartbeatMsg implements Body {

    @Override
    public byte[] serialize() {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}

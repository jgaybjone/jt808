package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import io.netty.buffer.ByteBuf;

/**
 * Created by jg.wang on 2020/4/9.
 * Description: 查询终端参数 0x8104
 */
@WritingMessageType(type = 0x8104 - 0x10000, needReply = true)
public class SettingQueryAllMsg implements Body {

    @Override
    public byte[] serialize() {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}

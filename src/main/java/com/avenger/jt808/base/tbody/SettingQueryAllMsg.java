package com.avenger.jt808.base.tbody;

import com.avenger.jt808.annotation.ReadingMessageType;
import com.avenger.jt808.annotation.WritingMessageType;
import com.avenger.jt808.base.Body;
import io.netty.buffer.ByteBuf;

/**
 * Created by jg.wang on 2020/4/9.
 * Description: 查询终端参数
 */
@WritingMessageType(type = (byte) 0x8104)
public class SettingQueryAllMsg implements Body {

    @Override
    public byte[] serialize() {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}

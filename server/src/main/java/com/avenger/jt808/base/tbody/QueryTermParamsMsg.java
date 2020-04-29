package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import io.netty.buffer.ByteBuf;

/**
 * Created by jg.wang on 2020/4/9.
 * Description: 查询终端参数
 */
@WritingMessageType(type = (short) 0x8107, needReply = true)
public class QueryTermParamsMsg implements Body {

    @Override
    public byte[] serialize() {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }

}

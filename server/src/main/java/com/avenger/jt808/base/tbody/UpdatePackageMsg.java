package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.domain.Body;
import io.netty.buffer.ByteBuf;

/**
 * Created by jg.wang on 2020/4/10.
 * Description:  todo 下发升级数据包待开发
 */
@WritingMessageType(type =0x8108- 0x10000)
public class UpdatePackageMsg implements Body {


    @Override
    public byte[] serialize() {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}

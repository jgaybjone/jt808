package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.ReadingMessageType;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.enums.ShootAtOnceResult;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/13.
 * Description:
 */
@ReadingMessageType(type = 0x0805)
@Data
public class ShootAtOnceRespMsg implements Body {

    /**
     * 应答流水号，对应终端下发时的流水号
     */
    private short respSerialNo;

    private ShootAtOnceResult result;

    /**
     * 多媒体 ID 列表
     */
    private List<Integer> mediaIds = new ArrayList<>();

    @Override
    public byte[] serialize() {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.respSerialNo = byteBuf.readShort();
        this.result = ShootAtOnceResult.valueOf(byteBuf.readByte());
        mediaIds.clear();
        final byte len = byteBuf.readByte();
        for (int i = 0; i < len; i++) {
            mediaIds.add(byteBuf.readInt());
        }

    }
}

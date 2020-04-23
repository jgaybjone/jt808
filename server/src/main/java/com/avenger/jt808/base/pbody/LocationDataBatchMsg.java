package com.avenger.jt808.base.pbody;

import com.avenger.jt808.base.annotation.ReadingMessageType;
import com.avenger.jt808.domain.Body;
import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/12.
 * Description: 定位数据批量上传
 */
@ReadingMessageType(type = 0x0704)
public class LocationDataBatchMsg implements Body {

    private byte type;

    private List<LocationAndAlarmMsg> locationAndAlarmMsgs = Collections.emptyList();

    /**
     * 是否盲区
     *
     * @return true是盲区 ，false 正常位置批量汇报
     */
    public boolean isBlindArea() {
        return type == 1;
    }

    public int getSize() {
        return locationAndAlarmMsgs.size();
    }

    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.locationAndAlarmMsgs = new ArrayList<>();
        final short size = byteBuf.readShort();
        this.type = byteBuf.readByte();
        for (int i = 0; i < size; i++) {
            final short len = byteBuf.readShort();
            final ByteBuf b = byteBuf.readBytes(len);
            final LocationAndAlarmMsg msg = new LocationAndAlarmMsg();
            msg.deSerialize(b);
            locationAndAlarmMsgs.add(msg);
        }
    }
}

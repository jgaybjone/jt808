package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.ReadingMessageType;
import com.avenger.jt808.enums.EventItem;
import com.avenger.jt808.enums.MultimediaEventType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/13.
 * Description:
 */
@ReadingMessageType(type = 0x0802)
public class QueryMediaRespMsg implements Body {

    /**
     * 应答流水号，对应终端下发时的流水号
     */
    @Getter
    private short respSerialNo;

    @Getter
    private List<MediaResp> mediaResps = new ArrayList<>();

    @Getter
    private ByteBuf pk;

    @Override
    public byte[] serialize() {
        return new byte[0];
    }

    @Override
    public void deSerializeSubpackage(ByteBuf byteBuf) {
        pk = Unpooled.wrappedBuffer(byteBuf);
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        byteBuf.markReaderIndex();
        deSerializeSubpackage(byteBuf);
        byteBuf.resetReaderIndex();
        this.respSerialNo = byteBuf.readShort();
        final int size = byteBuf.readUnsignedShort();
        for (int i = 0; i < size; i++) {
            final MediaResp resp = new MediaResp();
            resp.setId(byteBuf.readInt());
            resp.setType(MultimediaEventType.valueOf(byteBuf.readByte()));
            resp.setChannel(byteBuf.readByte());
            resp.setEventItem(EventItem.valueOf(byteBuf.readByte()));
            final LocationAndAlarmMsg location = new LocationAndAlarmMsg();
            location.deSerialize(byteBuf.readBytes(28));
            resp.setLocationAndAlarmMsg(location);
            this.mediaResps.add(resp);
        }
    }

    @Data
    public static class MediaResp {
        private int id;
        /**
         * 0:图像;1:音频;2:视频;
         */
        private MultimediaEventType type;

        private byte channel;

        private EventItem eventItem;

        private LocationAndAlarmMsg locationAndAlarmMsg;
    }
}

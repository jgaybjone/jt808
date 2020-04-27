package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.enums.EventType;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
@WritingMessageType(type = ((byte) 0x8301))
@Data
public class EventConfigMsg implements Body {

    /**
     * 0:删除终端现有所有事件，该命令后不带后继字节;
     * 1:更新事件;
     * 2:追加事件;
     * 3:修改事件;
     * 4:删除特定几项事件，之后事件项中无需带事件内容
     */
    private EventType type;

    /**
     * 若终端已有同 ID 的事件，则被覆盖
     */
    private List<Event> events = Collections.emptyList();


    @Override
    public byte[] serialize() {
        final ByteBuf byteBuf = Unpooled.buffer(200)
                .writeByte(type.getValue())
                .writeByte(events.size());
        events.forEach(event -> {
            final byte[] content = event.getContent().getBytes(Charset.forName("GBK"));
            byteBuf.writeByte(event.getId())
                    .writeByte(content.length)
                    .writeBytes(content);
        });
        return ByteBufUtils.array(byteBuf);
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }

    @Data
    public static class Event {
        /**
         * 事件 ID
         */
        private byte id;

        /**
         * 事件内容
         */
        private String content;
    }
}

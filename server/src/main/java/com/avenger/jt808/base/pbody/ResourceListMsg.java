package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.ReadingMessageType;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.base.tbody.QueryResourceListMsg;
import com.avenger.jt808.enums.ResourceType;
import com.avenger.jt808.util.ByteArrayUtils;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/18.
 * Description: 终端上传音视频资源列表
 */
@ReadingMessageType(type = 0x1205)
@Data
public class ResourceListMsg implements Body {

    /**
     * 应答流水号，对应终端下发时的流水号
     */
    private short respSerialNo;

    private List<Resource> resources;


    @Override
    public byte[] serialize() {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.respSerialNo = byteBuf.readShort();
        final int j = byteBuf.readInt();
        this.resources = new ArrayList<>(j);
        for (int i = 0; i < j; i++) {
            final Resource resource = new Resource();
            resource.setChannelId(byteBuf.readByte());
            resource.setStartAt(ByteArrayUtils.bcdToDate(byteBuf, 6));
            resource.setEndAt(ByteArrayUtils.bcdToDate(byteBuf, 6));
            resource.setAlarmDetail(new AlarmDetail(byteBuf.readInt()));
            resource.setAlarmExt(byteBuf.readInt());
            switch (byteBuf.readByte()) {
                case 0:
                    resource.setResourceType(ResourceType.AUDIO_AND_VIDEO);
                    break;
                case 1:
                    resource.setResourceType(ResourceType.AUDIO);
                    break;
                case 2:
                    resource.setResourceType(ResourceType.VIDEO);
                    break;
                default:
                    break;
            }
            switch (byteBuf.readByte()) {
                case 1:
                    resource.setBitStreamType(QueryResourceListMsg.BitStreamType.MAIN);
                    break;
                case 2:
                    resource.setBitStreamType(QueryResourceListMsg.BitStreamType.SUB);
                    break;
                default:
                    break;
            }
            switch (byteBuf.readByte()) {
                case 1:
                    resource.setMemType(QueryResourceListMsg.MemType.MAIN);
                    break;
                case 2:
                    resource.setMemType(QueryResourceListMsg.MemType.DISASTER_RECOVERY);
                    break;
                default:
                    break;
            }
            resource.setSize(byteBuf.readInt());
            resources.add(resource);
        }

    }

    @Data
    public static class Resource {
        private byte channelId;
        private LocalDateTime startAt;
        private LocalDateTime endAt;
        private AlarmDetail alarmDetail;

        private int alarmExt;

        private ResourceType resourceType;
        private QueryResourceListMsg.BitStreamType bitStreamType;
        private QueryResourceListMsg.MemType memType;

        private int size;
    }
}

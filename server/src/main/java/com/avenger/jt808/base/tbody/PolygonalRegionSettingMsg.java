package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.util.ByteArrayUtils;
import com.avenger.jt808.util.ByteBufUtils;
import com.avenger.jt808.util.DateUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/11.
 * Description: 设置多边形区域消息
 */
@EqualsAndHashCode(callSuper = true)
@WritingMessageType(type = ((short) 0x8604), needReply = true)
@Data
public class PolygonalRegionSettingMsg extends RegionCommon implements Body {


    private List<GeographicCoordinates> coordinates = Collections.emptyList();


    @Override
    public byte[] serialize() {
        final ByteBuf byteBuf = Unpooled.buffer(600)
                .writeInt(this.getId())
                .writeShort(super.getType())
                .writeByte(coordinates.size())
                .writeBytes(ByteArrayUtils.bcdStrToBytes(DateUtil.toStringByFormat(this.getStartTime(), "yy-MM-dd-HH-mm-ss")))
                .writeBytes(ByteArrayUtils.bcdStrToBytes(DateUtil.toStringByFormat(this.getEndTime(), "yy-MM-dd-HH-mm-ss")))
                .writeShort(this.getSpeedLimit())
                .writeByte(this.getOverSpeedDuration())
                .writeShort(coordinates.size());
        coordinates.forEach(coordinate -> byteBuf
                .writeInt(coordinate.getLatitude())
                .writeInt(coordinate.getLongitude()));
        return ByteBufUtils.array(byteBuf);
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }

    @Data
    public static class GeographicCoordinates {
        private int latitude;
        private int longitude;
    }
}

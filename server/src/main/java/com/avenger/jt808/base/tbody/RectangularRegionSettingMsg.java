package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.enums.RegionSettingType;
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
 * Description:
 */
@WritingMessageType(type = ((short) 0x8602), needReply = true)
@Data
public class RectangularRegionSettingMsg implements Body {

    /**
     * 0:更新区域;
     * 1:追加区域;
     * 2:修改区域
     */
    private RegionSettingType type;

    private List<RectangularRegion> rectangularRegions = Collections.emptyList();


    @Override
    public byte[] serialize() {
        final ByteBuf byteBuf = Unpooled.buffer(600)
                .writeByte(type.ordinal())
                .writeByte(rectangularRegions.size());
        rectangularRegions.forEach(circularRegion -> byteBuf
                .writeInt(circularRegion.getId())
                .writeShort(circularRegion.getType())
                .writeInt(circularRegion.getLatitudeOfUpperLeftPoint())
                .writeInt(circularRegion.getLongitudeOfUpperLeftPoint())
                .writeInt(circularRegion.getLatitudeOfUpperRightPoint())
                .writeInt(circularRegion.getLongitudeOfUpperRightPoint())
                .writeBytes(ByteArrayUtils.bcdStrToBytes(DateUtil.toStringByFormat(circularRegion.getStartTime(), "yy-MM-dd-HH-mm-ss")))
                .writeBytes(ByteArrayUtils.bcdStrToBytes(DateUtil.toStringByFormat(circularRegion.getEndTime(), "yy-MM-dd-HH-mm-ss")))
                .writeShort(circularRegion.getSpeedLimit())
                .writeByte(circularRegion.getOverSpeedDuration()));
        return ByteBufUtils.array(byteBuf);
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class RectangularRegion extends RegionCommon {

        /**
         * 左上点纬度
         */
        private int latitudeOfUpperLeftPoint;
        /**
         * 左上点经度
         */
        private int longitudeOfUpperLeftPoint;
        /**
         * 左上点纬度
         */
        private int latitudeOfUpperRightPoint;
        /**
         * 左上点经度
         */
        private int longitudeOfUpperRightPoint;


    }
}

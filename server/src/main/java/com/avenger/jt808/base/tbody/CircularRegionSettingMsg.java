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
@WritingMessageType(type = ((short) 0x8600))
@Data
public class CircularRegionSettingMsg implements Body {

    /**
     * 0:更新区域;
     * 1:追加区域;
     * 2:修改区域
     */
    private RegionSettingType type;

    private List<CircularRegion> circularRegions = Collections.emptyList();


    @Override
    public byte[] serialize() {
        final ByteBuf byteBuf = Unpooled.buffer(600)
                .writeByte(type.ordinal())
                .writeByte(circularRegions.size());
        circularRegions.forEach(circularRegion -> {
            byteBuf.writeInt(circularRegion.getId())
                    .writeShort(circularRegion.getType())
                    .writeInt(circularRegion.getCenterLatitude())
                    .writeInt(circularRegion.getCenterLongitude())
                    .writeInt(circularRegion.getRadius())
                    .writeBytes(ByteArrayUtils.bcdStrToBytes(DateUtil.toStringByFormat(circularRegion.getStartTime(), "yy-MM-dd-HH-mm-ss")))
                    .writeBytes(ByteArrayUtils.bcdStrToBytes(DateUtil.toStringByFormat(circularRegion.getEndTime(), "yy-MM-dd-HH-mm-ss")))
                    .writeShort(circularRegion.getSpeedLimit())
                    .writeByte(circularRegion.getOverSpeedDuration());

        });
        return ByteBufUtils.array(byteBuf);
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class CircularRegion extends RegionCommon {

        /**
         * 中心点纬度
         */
        private int centerLatitude;

        /**
         * 中心点经度
         */
        private int centerLongitude;

        /**
         * 半径
         */
        private int radius;


    }
}

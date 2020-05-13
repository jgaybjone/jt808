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
import java.util.Optional;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
@WritingMessageType(type = ((short) 0x8600), needReply = true)
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
                    .writeInt(circularRegion.getRadius());
            if (circularRegion.isTimeType()) {
                Optional.ofNullable(circularRegion.getStartTime())
                        .ifPresent(t -> byteBuf.writeBytes(ByteArrayUtils.bcdStrToBytes(DateUtil.toStringByFormat(t, "yyMMddHHmmss"))));
                Optional.ofNullable(circularRegion.getEndTime())
                        .ifPresent(t -> byteBuf.writeBytes(ByteArrayUtils.bcdStrToBytes(DateUtil.toStringByFormat(t, "yyMMddHHmmss"))));
            }

            if (circularRegion.isSpeedType()) {
                byteBuf.writeShort(circularRegion.getSpeedLimit())
                        .writeByte(circularRegion.getOverSpeedDuration());
            }

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

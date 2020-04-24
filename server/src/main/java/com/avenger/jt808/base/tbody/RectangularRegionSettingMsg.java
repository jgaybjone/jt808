package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.enums.RegionSettingType;
import com.avenger.jt808.util.ByteArrayUtils;
import com.avenger.jt808.util.DateUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
@WritingMessageType(type = ((byte) 0x8602))
@Data
public class RectangularRegionSettingMsg implements Body {

    /**
     * 0:更新区域;
     * 1:追加区域;
     * 2:修改区域
     */
    private RegionSettingType type;

    private List<RectangularRegion> circularRegions = Collections.emptyList();


    @Override
    public byte[] serialize() {
        final ByteBuf byteBuf = Unpooled.buffer(600)
                .writeByte(type.ordinal())
                .writeByte(circularRegions.size());
        circularRegions.forEach(circularRegion -> byteBuf
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
        return byteBuf.array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }

    @Data
    public static class RectangularRegion {
        /**
         * 区域ID
         */
        private int id;

        /**
         * 区域属性
         */
        private short type;

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

        /**
         * 起始时间
         * YY-MM-DD-hh-mm-ss，若区域属性0位为0则没有该 字段
         */
        private Date startTime;

        /**
         * 结束时间
         * YY-MM-DD-hh-mm-ss，若区域属性0位为0则没有该 字段
         */
        private Date endTime;

        /**
         * 最高速度
         * Km/h，若区域属性 1 位为 0 则没有该字段
         */
        private short speedLimit;

        /**
         * 超速持续时间
         * 单位为秒(s)(类似表述，同前修改)，若区域属性 1 位为 0 则没有该字段
         */
        private byte overSpeedDuration;

        public void timeType() {
            type = (short) (type | 0b1);
        }

        public void speedType() {
            type = (short) (type | 0b10);
        }

        public void alarmTheDriverWhenEnteringTheArea() {
            type = (short) (type | 0b100);
        }

        public void alarmThePlatformWhenEnteringTheArea() {
            type = (short) (type | 0b1000);
        }

        public void alarmTheDriverWhenOutTheArea() {
            type = (short) (type | 0b10000);
        }

        public void alarmThePlatformWhenOutTheArea() {
            type = (short) (type | 0b100000);
        }

        /**
         * 北纬
         */
        public void northLatitudeType() {
            type = (short) (type & 0b1111111110111111);
        }

        /**
         * 南纬
         */
        public void southLatitudeType() {
            type = (short) (type | 0b1000000);
        }

        /**
         * 东经
         */
        public void eastLongitudeType() {
            type = (short) (type & 0b1111111101111111);
        }

        /**
         * 西经
         */
        public void westLongitudeType() {
            type = (short) (type | 0b10000000);
        }

        /**
         * 允许开门
         */
        public void doorOpeningAllowedType() {
            type = (short) (type & 0b1111111011111111);
        }

        /**
         * 禁止开门
         */
        public void doorOpeningProhibitedType() {
            type = (short) (type | 0b100000000);
        }

        /**
         * 进区域开启通信模块
         */
        public void enableCommunicationModuleType() {
            type = (short) (type & 0b1011111111111111);
        }

        /**
         * 进区域关闭通信模块
         */
        public void disableCommunicationModuleType() {
            type = (short) (type | 0b100000000000000);
        }

        /**
         * 进区域不采集 GNSS 详细定位数据
         */
        public void notCollectGnssType() {
            type = (short) (type & 0b0111111111111111);
        }

        /**
         * 进区域采集 GNSS 详细定位数据
         */
        public void collectGnssType() {
            type = (short) (type | 0b1000000000000000);
        }

    }
}

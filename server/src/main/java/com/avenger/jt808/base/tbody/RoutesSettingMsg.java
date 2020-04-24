package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.util.ByteArrayUtils;
import com.avenger.jt808.util.DateUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/11.
 * Description: 设置路线消息体
 */
@WritingMessageType(type = ((byte) 0x8606))
@Data
public class RoutesSettingMsg implements Body {

    private int id;

    private short lineAttribute;

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

    private List<InflectionPoint> inflectionPoints = Collections.emptyList();

    /**
     * 根据时间
     */
    public void lineAttributeTime() {
        lineAttribute = (short) (lineAttribute | 0b1);
    }

    /**
     * 进路线报警给驾驶员
     */
    public void routeAlarmToDriver() {
        lineAttribute = (short) (lineAttribute | 0b100);
    }

    /**
     * 进路线报警给平台
     */
    public void routeAlarmToPlatform() {
        lineAttribute = (short) (lineAttribute | 0b1000);
    }

    /**
     * 出路线报警给驾驶员
     */
    public void routeOutAlarmToDriver() {
        lineAttribute = (short) (lineAttribute | 0b10000);
    }

    /**
     * 出路线报警给平台
     */
    public void routeOutAlarmToPlatform() {
        lineAttribute = (short) (lineAttribute | 0b100000);
    }


    @Override
    public byte[] serialize() {
        final ByteBuf byteBuf = Unpooled.buffer(500).writeInt(id)
                .writeShort(this.lineAttribute)
                .writeBytes(ByteArrayUtils.bcdStrToBytes(DateUtil.toStringByFormat(this.getStartTime(), "yy-MM-dd-HH-mm-ss")))
                .writeBytes(ByteArrayUtils.bcdStrToBytes(DateUtil.toStringByFormat(this.getEndTime(), "yy-MM-dd-HH-mm-ss")))
                .writeShort(inflectionPoints.size());
        if (!CollectionUtils.isEmpty(inflectionPoints)) {
            inflectionPoints.forEach(point -> {
                byteBuf.writeInt(point.getId())
                        .writeInt(point.getRouteId())
                        .writeInt(point.getLatitude())
                        .writeInt(point.getLongitude())
                        .writeByte(point.getRoadWidth())
                        .writeByte(point.getRoadAttr())
                        .writeShort(point.getMaxRoadTravel())
                        .writeShort(point.getMinRoadTravel())
                        .writeShort(point.getMaxSpeed())
                        .writeByte(point.getOverspeedDuration());

            });
        }

        return byteBuf.array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }

    @Data
    public static class InflectionPoint {

        /**
         * 拐点ID
         */
        private int id;
        /**
         * 路段ID
         */
        private int routeId;

        /**
         * 纬度
         */
        private int latitude;
        /**
         * 经度
         */
        private int longitude;

        /**
         * 路段宽度
         */
        private byte roadWidth;

        /**
         * 路段属性
         */
        private byte roadAttr;
        /**
         * 路段行驶过长阈值
         */
        private short maxRoadTravel;

        /**
         * 路段行驶不足阈值
         */
        private short minRoadTravel;

        /**
         * 路段最高速度
         */
        private short maxSpeed;

        /**
         * 路段超速持续时间
         */
        private byte overspeedDuration;


        /**
         * 路段属性
         *
         * @param drivingTime   行驶时间
         * @param speedLimit    是否限速
         * @param northLatitude 是否北纬
         * @param eastLongitude 是否东经
         */
        public void configRoadAttr(boolean drivingTime, boolean speedLimit, boolean northLatitude, boolean eastLongitude) {
            if (drivingTime) {
                roadAttr = (byte) (roadAttr | 0b1);
            }
            if (speedLimit) {
                roadAttr = (byte) (roadAttr | 0b10);
            }
            if (northLatitude) {
                roadAttr = (byte) (roadAttr & 0b11111011);
            } else {
                roadAttr = (byte) (roadAttr | 0b100);
            }
            if (eastLongitude) {
                roadAttr = (byte) (roadAttr & 0b11110111);
            } else {
                roadAttr = (byte) (roadAttr | 0b1000);
            }
        }


    }

}

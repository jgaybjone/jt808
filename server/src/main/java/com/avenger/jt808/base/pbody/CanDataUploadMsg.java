package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.ReadingMessageType;
import com.avenger.jt808.util.ByteArrayUtils;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/12.
 * Description: CAN总线数据上传
 */
@ReadingMessageType(type = 0x0705)
@Data
public class CanDataUploadMsg implements Body {

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("HH-mm-ss");


    private LocalTime time;

    private List<CanInfo> canInfos = Collections.emptyList();

    public int getSize() {
        return canInfos.size();
    }

    @Override
    public byte[] serialize() {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        final short size = byteBuf.readShort();
        String timeStr = ByteArrayUtils.toBcdString(byteBuf, 5);
        if (timeStr.length() > 12) {
            timeStr = timeStr.substring(0, timeStr.lastIndexOf("-"));
        }
        this.time = LocalTime.parse(timeStr, DF);
        List<CanInfo> infos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            final CanInfo canInfo = new CanInfo();
            final int can = byteBuf.readInt();
            canInfo.setCanId(can & 0b00011111111111111111111111111111);
            canInfo.setAveraged((can & 0b00100000000000000000000000000000) > 0);
            canInfo.setStandardFramed((can & 0b01000000000000000000000000000000) == 0);
            canInfo.setChannel(can > 0 ? 0 : 1);
            final byte[] d = new byte[8];
            byteBuf.readBytes(d);
            canInfo.setData(d);
            infos.add(canInfo);
        }
        this.canInfos = infos;
    }

    @Data
    public static class CanInfo {

        /**
         * bit28-bit0 表示 CAN 总线 ID。
         */
        private int canId;

        /**
         * bit29 表示数据采集方式，0:原始数据，1:采 集区间的平均值;
         */
        private boolean averaged;

        /**
         * bit30 表示帧类型，0:标准帧，1:扩展帧;
         */
        private boolean standardFramed;

        /**
         * 0:CAN1，1:CAN2;
         */
        private int channel;


        private byte[] data;

    }
}

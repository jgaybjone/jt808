package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.ReadingMessageType;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.util.ByteArrayUtils;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

/**
 * Created by jg.wang on 2020/4/18.
 * Description: 乘客上传流量
 */
@ReadingMessageType(type = 0x1005)
@Data
public class PassengerFlowUploadMsg implements Body {

    /**
     * 起始时间
     */
    private LocalDateTime startAt;

    /**
     * 结束时间
     */
    private LocalDateTime endAt;
    /**
     * 上车人数
     */
    private short hopOnNum;
    /**
     * 下车人数
     */
    private short hopOffNum;

    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.startAt = ByteArrayUtils.bcdToDate(byteBuf, 6);
        this.endAt = ByteArrayUtils.bcdToDate(byteBuf, 6);
        this.hopOnNum = byteBuf.readShort();
        this.hopOffNum = byteBuf.readShort();
    }
}

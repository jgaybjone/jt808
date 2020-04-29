package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/12.
 * Description:
 */
@WritingMessageType(type = (0x8701 - 0x10000))
@Data
public class DrivingRecordAttrMsg implements Body {

    /**
     * 命令字
     * 命令字	功  能	记录仪回送的数据块数据
     * 00H	采集记录仪执行标准版本	记录仪执行标准的年号及修改单号
     * 01H	采集当前驾驶人信息	当前驾驶人的机动车驾驶证号码
     * 02H	采集记录仪实时时间	实时时间
     * 03H	采集累计行驶里程	实时时间、安装时的初始里程及安装后的累计行驶里程
     * 04H	采集记录仪脉冲系数	实时时间及设定的脉冲系数
     * 05H	采集车辆信息	车辆识别代号、机动车号牌号码和机动车号牌分类
     * 06H	采集记录仪状态信号配置信息	状态信号配置信息
     * 07H	采集记录仪唯一性编号	唯一性编号及初次安装日期
     * 08H	采集指定的行驶速度记录	符合条件的行驶速度记录
     * 09H	采集指定的位置信息记录	符合条件的位置信息记录
     * 10H	采集指定的事故疑点记录	符合条件的事故疑点记录
     * 11H	采集指定的超时驾驶记录	符合条件的超时驾驶记录
     * 12H	采集指定的驾驶人身份记录	符合条件的驾驶人登录退出记录
     * 13H	采集指定的外部供电记录	符合条件的供电记录
     * 14H	采集指定的参数修改记录	符合条件的参数修改记录
     * 15H	采集指定的速度状态日志	符合条件的速度状态日志
     * 16H～1FH	预留	预留
     */
    private byte commandWord;

    private byte[] data;

    @Override
    public byte[] serialize() {
        return ByteBufUtils.array(Unpooled.buffer(400)
                .writeByte(commandWord)
                .writeBytes(data));
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}

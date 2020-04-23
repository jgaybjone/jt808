package com.avenger.jt808.base.pbody;

import com.avenger.jt808.base.annotation.ReadingMessageType;
import com.avenger.jt808.domain.Body;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.nio.charset.Charset;

/**
 * Created by jg.wang on 2020/4/9.
 * Description:
 */
@ReadingMessageType(type = 0x0100)
@Data
public class RegisterMsg implements Body {

    /**
     * 省域ID
     */
    private short provinceId;

    /**
     * 市、县域ID
     */
    private short cityId;

    /**
     * 制造商ID
     */
    private String manufactureId;

    /**
     * 终端型号
     */
    private String terminalModel;
    /**
     * 终端ID
     */
    private String terminalId;

    /**
     * 车牌颜色 1 蓝色，2 黄色，3 黑色，4白色，9 其他颜色
     */
    private byte plateColor = 0;

    /**
     * 车牌号码
     */
    private String plateNo;

    /**
     * 车牌颜色为 0 时，表示车辆 VIN; 否则，表示公安交通管理部门颁发的机动车号牌。
     * 车辆vin码
     */
    public String getVin() {
        if (plateColor == 0) {
            return plateNo;
        }
        return null;
    }

    @Override
    public byte[] serialize() {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf buffer) {
        this.provinceId = buffer.readShort();
        this.cityId = buffer.readShort();
        final byte[] no = new byte[5];
        buffer.readBytes(no);
        this.manufactureId = new String(no, Charset.forName("GBK"));
        final byte[] no2 = new byte[20];
        buffer.readBytes(no2);
        this.terminalModel = new String(no2, Charset.forName("GBK")).trim();
        final byte[] no3 = new byte[7];
        buffer.readBytes(no3);
        this.terminalId = new String(no3);
        this.plateColor = buffer.readByte();
        final byte[] no4 = new byte[buffer.readableBytes()];
        buffer.readBytes(no4);
        this.plateNo = new String(no4, Charset.forName("GBK"));
    }
//
//    public static void main(String[] args) {
//        final String code = "7e0001000501585166372016e520689208005c7e";
//        final int length = code.length();
//        List<String> l = new ArrayList<>();
//        for (int i = 0; i < length; i++) {
//            l.add(code.substring(i, ++i + 1));
//        }
//        final byte[] bytes = new byte[length / 2];
//        final List<Byte> collect = l.stream().map(i -> (byte) Integer.parseInt(i, 16)).collect(Collectors.toList());
//        for (int i = 0; i < collect.size(); i++) {
//            bytes[i] = collect.get(i);
//        }
//        final RegisterMsg registerMsg = new RegisterMsg();
//        registerMsg.deSerialize(bytes);
//        System.out.println(bytes);
//    }
}

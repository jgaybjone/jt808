package com.avenger.jt808.base.pbody;

import com.avenger.jt808.annotation.ReadingMessageType;
import com.avenger.jt808.base.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/10.
 * Description:
 */
@SuppressWarnings("rawtypes")
@ReadingMessageType(type = 0x0104)
@Data
public class SettingDetailMsg implements Body {

    /**
     * 应答流水号，对应终端下发时的流水号
     */
    private short respSerialNo;

    private List<SettingParams> settingParams = Collections.emptyList();

    public int getSize() {
        return settingParams.size();
    }

    @Override
    public byte[] serialize() {
        final ByteBuf buffer = Unpooled.buffer(200);
        buffer.writeShort(respSerialNo);
        paramsSerialize(this.settingParams, buffer);
        return new byte[0];
    }

    public static void paramsSerialize(List<SettingParams> settingParams, ByteBuf buffer) {
        settingParams.forEach(p -> {
            final Class realType = p.getRealType();
            buffer.writeInt(p.getId());
            if (Integer.class.isAssignableFrom(realType)) {
                buffer.writeByte(4);
                buffer.writeInt((Integer) p.getParam());
            } else if (Byte.class.isAssignableFrom(realType)) {
                buffer.writeByte(1);
                buffer.writeByte((Byte) p.getParam());
            } else if (Short.class.isAssignableFrom(p.getRealType())) {
                buffer.writeByte(2);
                buffer.writeShort((Short) p.getParam());
            } else if (String.class.isAssignableFrom(p.getRealType())) {
                final String param = (String) p.getParam();
                final byte[] bytes = param.getBytes(Charset.forName("GBK"));
                buffer.writeByte(bytes.length);
                buffer.writeBytes(bytes);
            } else if (Long.class.isAssignableFrom(p.getRealType())) {
                buffer.writeByte(8);
                buffer.writeLong(((Long) p.getParam()));
            } else if (byte[].class.isAssignableFrom(p.getRealType())) {
                final byte[] param = (byte[]) p.getParam();
                buffer.writeByte(param.length);
                buffer.writeBytes(param);
            } else {
                throw new IllegalArgumentException("参数类型不合法:" + p.getRealType().getName());
            }
        });
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.respSerialNo = byteBuf.readShort();
        this.settingParams = params(byteBuf);
    }

    public static List<SettingParams> params(ByteBuf byteBuf) {
        final byte size = byteBuf.readByte();
        List<SettingParams> params = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            final int id = byteBuf.readInt();
            final byte len = byteBuf.readByte();
            SettingParams p;
            switch (len) {
                case 1:
                    p = new ByteSettingParams(id, byteBuf.readByte());
                    break;
                case 2:
                    p = new ShortSettingParams(id, byteBuf.readShort());
                    break;
                case 4:
                    p = new IntSettingParams(id, byteBuf.readInt());
                    break;
                case 8:
                    p = new LongSettingParams(id, byteBuf.readLong());
                    break;
                default:
                    throw new IllegalArgumentException("不支持的参数长度" + len);
            }
            params.add(p);
        }
        return params;
    }
}

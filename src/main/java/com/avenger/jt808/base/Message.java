package com.avenger.jt808.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/9.
 * Description:
 */
@Data
public class Message  {

    private Header header;

    private Body msgBody;

//    @Override
//    public byte[] serialize() {
//        final ByteBuf buffer = Unpooled.buffer(200);
//        final ByteBuf buffer1 = Unpooled.buffer(220);
//        final byte[] b = msgBody.serialize();
//        buffer.writeBytes(header.getRaw((byte) b.length));
//        buffer.writeBytes(b);
//        while (buffer.isReadable()) {
//            final byte c = buffer.readByte();
//            switch (c) {
//                case 0x7D:
//                    buffer1.writeShort(0x7D01);
//                    break;
//                case 0x7E:
//                    buffer1.writeShort(0x7D02);
//                    break;
//                default:
//                    buffer1.writeShort(c);
//                    break;
//            }
//        }
//        return buffer1.array();
//    }
//
//    @Override
//    public void deSerialize(ByteBuf byteBuf) {
//
//    }
}

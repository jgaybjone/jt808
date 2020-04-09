package com.avenger.jt808.server;

import com.avenger.jt808.base.Header;
import com.avenger.jt808.base.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by jg.wang on 2020/4/9.
 * Description: 消息序列化处理
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        final ByteBuf buffer = Unpooled.buffer(200);
        final Header header = msg.getHeader();
        final byte[] b = msg.getMsgBody().serialize();
        buffer.writeBytes(header.getRaw((byte) b.length));
        buffer.writeBytes(b);
        while (buffer.isReadable()) {
            final byte c = buffer.readByte();
            switch (c) {
                case 0x7D:
                    out.writeShort(0x7D01);
                    break;
                case 0x7E:
                    out.writeShort(0x7D02);
                    break;
                default:
                    out.writeShort(c);
                    break;
            }
        }
    }
}

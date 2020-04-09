package com.avenger.jt808.server;

import com.avenger.jt808.base.Message;
import com.avenger.jt808.base.MessageFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Created by jg.wang on 2020/4/9.
 * Description:
 */
public class MessageDecoder extends ReplayingDecoder<Void> {

    MessageFactory messageFactory;

    public MessageDecoder(MessageFactory messageFactory) {
        super();
        this.messageFactory = messageFactory;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        final byte b = in.readByte();
        if (b != 0x7E) {
            return;
        }
        final ByteBuf buffer = Unpooled.buffer(100);
        while (true) {
            final byte cu = in.readByte();
            if (cu == 0x7E) {
                break;
            } else if (cu == 0x7D) {//处理转义
                final byte cu2 = in.readByte();
                if (cu2 == 0x02) {
                    buffer.writeByte(0x7E);
                } else if (cu2 == 0x01) {
                    buffer.writeByte(0x7D);
                } else {
                    buffer.writeByte(cu).writeByte(cu2);
                }
            } else {
                buffer.writeByte(cu);
            }
        }
        final Message message = messageFactory.create(buffer);
        out.add(message);

    }

//    public static void main(String[] args) {
//        final ByteBuf buffer = Unpooled.buffer(1);
//        buffer.writeByte(0x7e);
//        final byte[] array = buffer.array();
//        System.out.println(array);
//    }
}

package com.avenger.jt808.server;

import com.avenger.jt808.base.MessageFactory;
import com.avenger.jt808.domain.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by jg.wang on 2020/4/9.
 * Description:
 */
@Slf4j
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
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("7e");
        final ByteBuf buffer = Unpooled.buffer(100);
        while (true) {
            final byte cu = in.readByte();
            final String hex = Integer.toHexString(cu);
            if (hex.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hex);
            if (cu == 0x7E) {
                break;
            } else if (cu == 0x7D) {//处理转义
                final byte cu2 = in.readByte();
                final String hex2 = Integer.toHexString(cu2);
                if (hex2.length() < 2) {
                    stringBuilder.append(0);
                }
                stringBuilder.append(hex2);
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
        message.setVerified(this.check(buffer.resetReaderIndex()));
        out.add(message);
        if (log.isDebugEnabled()) {
            log.debug("消息解析成功，消息类型：{}" +
                    "\n>>>>>>>>>>>>>detail: {}", Integer.toHexString(message.getHeader().getId()), message.toString());
        }


    }


    private boolean check(ByteBuf byteBuf) {
        final int len = byteBuf.readableBytes();
        int check = 0;
        for (int i = 0; i < len - 1; i++) {
            check = check ^ byteBuf.readByte();
        }
        return byteBuf.readByte() == (byte) check;
    }

//    public static void main(String[] args) {
//        final ByteBuf buffer = Unpooled.buffer(1);
//        buffer.writeByte(0x7e);
//        buffer.writeByte(89);
//        buffer.writeByte(88);
//        System.out.println(buffer.readByte());
//        final byte[] array = ByteBufUtils.array(byteBuf);
//        System.out.println(array);
//    }
}

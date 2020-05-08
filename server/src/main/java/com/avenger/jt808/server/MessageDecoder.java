package com.avenger.jt808.server;

import com.avenger.jt808.base.MessageFactory;
import com.avenger.jt808.domain.Message;
import com.avenger.jt808.util.JsonUtils;
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
                if (cu2 == 0x02) {
                    buffer.writeByte(0x7E);
                    stringBuilder.append("7E");
                } else if (cu2 == 0x01) {
                    buffer.writeByte(0x7D);
                    stringBuilder.append("7D");
                } else {
                    log.warn("解码错误0x7D后面没有01也没有02");
                }
            } else {
                buffer.writeByte(cu);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug(" raw data: {}", stringBuilder.toString().replaceAll("ffffff", ""));
        }
        final Message message = messageFactory.create(buffer);
        if (message == null) {
            log.info("未处理完所有分包！");
            return;
        }
        message.setVerified(this.check(buffer.resetReaderIndex()));
        out.add(message);
        if (log.isDebugEnabled()) {
            log.debug("处理消息：{}", JsonUtils.objToJsonStr(message));
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

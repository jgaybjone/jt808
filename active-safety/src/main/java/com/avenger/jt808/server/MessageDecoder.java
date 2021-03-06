package com.avenger.jt808.server;

import com.avenger.jt808.AlarmAccessoriesMsg;
import com.avenger.jt808.AttachmentInformationMsg;
import com.avenger.jt808.FileData;
import com.avenger.jt808.UploadCompleteMsg;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.Header;
import com.avenger.jt808.domain.Message;
import com.avenger.jt808.util.ByteBufUtils;
import com.avenger.jt808.util.JsonUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by jg.wang on 2020/4/24.
 * Description:
 */
@Slf4j
public class MessageDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();
        int flag = in.readInt();
        if (flag == 0x30316364) {
            final FileData fileData = new FileData();
            fileData.setFileName(ByteBufUtils.toStringWithGBK(in, 50).trim());
            fileData.setOffset(in.readUnsignedInt());
            final byte[] data = new byte[in.readInt()];
            in.readBytes(data);
            fileData.setData(data);
            out.add(fileData);
            return;
        }
        in.resetReaderIndex();

        final byte b = in.readByte();
        if (b != 0x7E) {
            return;
        }
        final ByteBuf buffer = Unpooled.buffer(100);
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("7e");
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

        final Header header = new Header(buffer);
        Body body;
        switch (header.getId()) {
            case 0x1210:
                body = new AlarmAccessoriesMsg();
                break;
            case 0x1211:
                body = new AttachmentInformationMsg();
                break;
            case 0x1212:
                body = new UploadCompleteMsg();
                break;
            default:
                throw new UnsupportedMessageTypeException("不支持的类型：" + header.getId());
        }
        body.deSerialize(buffer);
        final Message message = new Message();
        message.setHeader(header);
        message.setMsgBody(body);
        out.add(message);
        if (log.isDebugEnabled()) {
            log.debug("处理消息：{}, raw data: {}", JsonUtils.objToJsonStr(message), stringBuilder.toString().replaceAll("ffffff", ""));
        }
    }

}

package com.avenger.jt808.server;

import com.avenger.jt808.FileData;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Created by jg.wang on 2020/4/24.
 * Description: 文件数据包解码
 */

public class FileDataDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int flag = in.readInt();
        if (flag != 0x30316364) {
            return;
        }
        final FileData fileData = new FileData();
        fileData.setFileName(ByteBufUtils.toStringWithGBK(in, 50).trim());
        fileData.setOffset(in.readUnsignedInt());
        final byte[] data = new byte[in.readInt()];
        in.readBytes(data);
        fileData.setData(data);
        out.add(fileData);
    }
}

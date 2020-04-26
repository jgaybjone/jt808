package com.avenger.jt808.server;

import com.avenger.jt808.FileData;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * Created by jg.wang on 2020/4/24.
 * Description:
 */
@RequiredArgsConstructor
@Slf4j
@ChannelHandler.Sharable
public class FileDataChannelInHandler extends SimpleChannelInboundHandler<FileData> {

    @NonNull
    private final String uploadDir;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileData msg) throws Exception {
        final File file = new File(uploadDir + msg.getFileName());
        if (file.exists()) {
            log.info("写入文件: {}, file size : {}, package offset : {}", file.getPath(), file.list(), msg.getOffset());
//            if (file.length() == msg.getOffset()) {
//                FileUtils.writeByteArrayToFile(file, msg.getData(), true);
//            } else {
//                log.warn("偏移量错误");
        }
//        } else {
        FileUtils.writeByteArrayToFile(file, msg.getData(), true);
//        }
    }

}

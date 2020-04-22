package com.avenger.jt808.server;

import com.avenger.jt808.base.Message;
import com.avenger.jt808.base.MessageFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by jg.wang on 2020/4/9.
 * Description:
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TermServer {

    @Value("${term-port:9119}")
    private int termPort;

    @NonNull
    private final MessageFactory messageFactory;

    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;
    ChannelFuture f;
    ServerBootstrap b;

    @PostConstruct
    public boolean start() {
//        createMsg();
        bossGroup = new NioEventLoopGroup(); // (1)
        workerGroup = new NioEventLoopGroup();
        //
        try {
            b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            //超过20分钟未收到客户端消息则自动断开客户端连接
                            ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(20, 0, 0, TimeUnit.MINUTES));
                            ch.pipeline().addLast("decoder", new MessageDecoder(messageFactory));
                            ch.pipeline().addLast(new MessageHandler());
                            ch.pipeline().addLast(new MessageEncoder());
                            ch.pipeline().addLast("encoder", new ByteArrayEncoder());
                            //handler.setConnctionMap(connctionMap);
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128) // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, false); // (6)

            // 绑定端口，开始接收进来的连接
            f = b.bind(termPort).sync();

            // 等待服务器 socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            // f.channel().closeFuture().sync();
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    @PreDestroy
    public void Stop() {
        log.info("netty server stop");
        try {
            // f.channel().closeFuture().sync();
            f.channel().close();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        try {
            workerGroup.shutdownGracefully();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        try {
            bossGroup.shutdownGracefully();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

//    public void createMsg() {
//        final String code = "01040226018168953558321f7fc1340000000104000000b4000000020400000005000000030400000003000000040400000000000000050400000000" +
//                "0000000604000000000000000704000000000000001005434d4e455400000011000000001200000000130e3132312e34302e3136332e32343000000014000000001500000000160000000017000000001804000092eb0000001904000092eb0000002004000000000" +
//                "0000021040000000000000022040000000000000028040000000000000027040000007800000029040000001e0000002c040000000000000050040000000000000052040000000000000053040000000000000055040000007800000056040000000a000000570400" +
//                "0000000000005904000003840000005a04000000000000005b0200320000005c02070800000070040000000000000071040000000000000072040000000000000073040000000000000074040000000000000075150003001919000001800005004b1900000400001" +
//                "f01000000761304000001010000020200000303000004040000000000775504010003001919000001800005004b1900000400001f020003001919000001800005004b1900000400001f030003001919000001800005004b1900000400001f04000300191900000180" +
//                "0005004b1900000400001f0000008004000000000000008102002c0000008202012f000000830cd4c14231323334350000000000000084010100000090010300001018010100001022040801000500001023010005";
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
//        final ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
//        try {
//            final Message message = messageFactory.create(byteBuf);
//            log.info(message.toString());
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
//        System.out.println(bytes);
//    }


}

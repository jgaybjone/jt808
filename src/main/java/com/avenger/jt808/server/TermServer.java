package com.avenger.jt808.server;

import com.avenger.jt808.base.MessageFactory;
import io.netty.bootstrap.ServerBootstrap;
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
import java.util.concurrent.TimeUnit;

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
//        final String code = "0001000501585166372016e520689208005c";
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
//            final Message message = messageUtils.create(byteBuf);
//            log.info(message.toString());
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
//        System.out.println(bytes);
//    }

}

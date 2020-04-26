package com.avenger.jt808.server;

import com.avenger.jt808.handler.AlarmAccessoriesHandler;
import com.avenger.jt808.handler.AttachmentInformationHandler;
import com.avenger.jt808.handler.UploadFinishHandler;
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
import io.vertx.core.impl.VertxImpl;
import io.vertx.rabbitmq.RabbitMQClient;
import io.vertx.rabbitmq.RabbitMQOptions;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * Created by jg.wang on 2020/4/24.
 * Description:
 */
@Slf4j
public class TermServer {

    private int termPort = 9290;

    private SimpleChannelMessageHandler messageHandler;

    private FileDataChannelInHandler fileDataChannelInHandler;


    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;
    ChannelFuture f;
    ServerBootstrap b;

    private void start() {
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
                            ch.pipeline().addLast("messageDecoder", new MessageDecoder());
                            ch.pipeline().addLast("messageHandler", messageHandler);
                            ch.pipeline().addLast("fileDataChannelInHandler", fileDataChannelInHandler);
                            ch.pipeline().addLast("messageEncoder", new MessageEncoder());
                            ch.pipeline().addLast("encoder", new ByteArrayEncoder());
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128) // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, false); // (6)

            // 绑定端口，开始接收进来的连接
            f = b.bind(termPort).sync();

            // 等待服务器 socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            // f.channel().closeFuture().sync();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public static void main(String[] args) {
        final TermServer termServer = new TermServer();
        String uploadDir = "/data/jt808/upload/";
        final String env = System.getProperty("env", "dev");
        ResourceBundle resource = ResourceBundle.getBundle("application-" + env);
        final RabbitMQClient rabbit = rabbit(resource);
        rabbit.start(ar -> {
            if (ar.succeeded()) {
                log.info("MQ client starting succeed!");
            } else {
                log.error("Could not start MQ client!");
            }
        });
//
//        final HashMap<String, Object> payload = new HashMap<>();
//        payload.put("body", "Hello RabbitMQ, from Vert.x !");
//        for (int i = 0; i < 1; i++) {
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            rabbit.basicPublish("", "rabbit_test", JsonObject.mapFrom(payload), r -> {
//                if (r.succeeded()) {
//                    log.info("push success");
//                } else {
//                    r.cause().printStackTrace();
//                }
//            });
//        }
        if (args != null && args.length > 0) {
            termServer.termPort = Integer.parseInt(args[0]);
            if (args.length > 2)
                uploadDir = args[1];
        }
        final List<MessageHandler> messageHandlers = new ArrayList<>();
        final AlarmAccessoriesHandler alarmAccessoriesHandler = new AlarmAccessoriesHandler();
        final AttachmentInformationHandler attachmentInformationHandler = new AttachmentInformationHandler();
        final UploadFinishHandler uploadFinishHandler = new UploadFinishHandler();
        messageHandlers.add(alarmAccessoriesHandler);
        messageHandlers.add(attachmentInformationHandler);
        messageHandlers.add(uploadFinishHandler);
        final MessageHandlerManager messageHandlerManager = new MessageHandlerManager(messageHandlers);
        termServer.messageHandler = new SimpleChannelMessageHandler(messageHandlerManager);
        termServer.fileDataChannelInHandler = new FileDataChannelInHandler(uploadDir);
        termServer.start();
        log.info("active safety server started!");
    }

    private static RabbitMQClient rabbit(ResourceBundle resource) {
        RabbitMQOptions config = new RabbitMQOptions();
// Each parameter is optional
// The default parameter with be used if the parameter is not set
        config.setUser(resource.getString("spring.rabbitmq.username"));
        config.setPassword(resource.getString("spring.rabbitmq.password"));
        config.setHost(resource.getString("spring.rabbitmq.host"));
        config.setPort(5673);
        config.setVirtualHost("/");
        config.setConnectionTimeout(6000); // in milliseconds
        config.setRequestedHeartbeat(60); // in seconds
        config.setHandshakeTimeout(6000); // in milliseconds
        config.setRequestedChannelMax(5);
        config.setNetworkRecoveryInterval(500); // in milliseconds
        config.setAutomaticRecoveryEnabled(true);

        return RabbitMQClient.create(VertxImpl.factory.vertx(), config);
    }


}

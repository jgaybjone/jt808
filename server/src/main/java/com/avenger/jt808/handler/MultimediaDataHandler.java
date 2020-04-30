package com.avenger.jt808.handler;

import com.avenger.jt808.base.pbody.MultimediaData;
import com.avenger.jt808.base.tbody.MediaDataUploadMsg;
import com.avenger.jt808.domain.EncryptionForm;
import com.avenger.jt808.domain.Header;
import com.avenger.jt808.domain.Message;
import com.avenger.jt808.domain.entity.GpsRecord;
import com.avenger.jt808.domain.entity.Multimedia;
import com.avenger.jt808.service.MultimediaService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by jg.wang on 2020/4/30.
 * Description: 多媒体数据包处理
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MultimediaDataHandler implements MessageHandler {

    @Value("${upload.dir:/data/jt808/upload/}")
    private String uploadDir;

    @NonNull
    private final MultimediaService multimediaService;

//    @NonNull
//    private RedisTemplate redisTemplate;


    @Override
    public short getId() {
        return 0x0801;
    }

    @Override
    public Publisher<Message> process(Message m) {
        return Mono.justOrEmpty(m)
                .filter(message -> message.getMsgBody() instanceof MultimediaData)
                .map(message -> {
                    final Header h = message.getHeader();
                    final MultimediaData b = ((MultimediaData) message.getMsgBody());
                    final short number = h.getPacketNumber();
                    final short count = h.getPacketsCount();
                    final String fileName = uploadDir + "_" + b.getId() + "_" + b.getType().ordinal() + "_"
                            + "_" + h.getSimNo() + "_" + b.getChannelId() + "_"
                            + b.getEventItem().ordinal() + "." + b.getFormat().name().toLowerCase();
                    Message m2 = null;
                    log.info("开始保存没提文件数据到文件：{}", fileName);
                    try {
                        FileUtils.writeByteArrayToFile(new File(fileName), b.getData(), true);
                        if (count == number) {
                            final MediaDataUploadMsg body = new MediaDataUploadMsg();
                            body.setId(b.getId());
                            final Header header = new Header(h.getSimNo(), false, EncryptionForm.NOTHING);
                            m2 = new Message();
                            m2.setHeader(header);
                            m2.setMsgBody(body);
                            GpsRecord record = LocationAndAlarmMsgHandler.fetch(h, b.getLocationAndAlarmMsg());
                            Multimedia multimedia = Multimedia
                                    .builder()
                                    .multimediaId(b.getId())
                                    .type(b.getType())
                                    .encodingFormat(b.getFormat())
                                    .simNo(h.getSimNo())
                                    .eventType(b.getEventItem())
                                    .channel(((int) b.getChannelId()))
                                    .time(Timestamp.valueOf(b.getLocationAndAlarmMsg().getTime()))
                                    .build();
                            multimediaService.save(multimedia, record);

                        }
                    } catch (IOException e) {
                        log.error("文件保存错误: count = " + count + ", number = " + number, e);
                    }
                    return m2;
                });
    }
}

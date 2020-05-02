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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.channels.FileChannel;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by jg.wang on 2020/4/30.
 * Description: 多媒体数据包处理
 */
@SuppressWarnings("BlockingMethodInNonBlockingContext")
@Component
@Slf4j
@RequiredArgsConstructor
public class MultimediaDataHandler implements MessageHandler {

    @Value("${upload.dir:/data/jt808/upload/}")
    private String uploadDir;

    @NonNull
    private final MultimediaService multimediaService;

    @NonNull
    private final StringRedisTemplate stringRedisTemplate;


    @Override
    public short getId() {
        return 0x0801;
    }

    @Override
    public Publisher<Message> process(Message m) {
        return Mono.justOrEmpty(m)
                .filter(Objects::nonNull)
                .filter(message -> message.getMsgBody() instanceof MultimediaData)
                .flatMap(message -> {
                    final Header h = message.getHeader();
                    final MultimediaData b = ((MultimediaData) message.getMsgBody());
                    final short number = h.getPacketNumber();
                    final short count = h.getPacketsCount();
                    log.info("save file count = {}, number = {}", count, number);
                    Message m2 = null;
                    String fileName;
                    final String key = h.getSimNo() + "_" + h.getId() + "_" + h.getPacketsCount() + "_multime_diadata";
                    fileName = "/tmp/" + h.getSimNo() + "_"
                            + h.getId() + "_" + h.getPacketsCount() + "/" + number + ".data";
                    stringRedisTemplate.opsForHash().put(key, "" + number, fileName);
                    if (1 == number) {
                        stringRedisTemplate.expire(key, 20, TimeUnit.MINUTES);
                        final String url = uploadDir + h.getSimNo() + "_"
                                + h.getId() + "_" + h.getPacketsCount() + "_" + System.currentTimeMillis() + "." + b.getFormat().name().toLowerCase();
                        stringRedisTemplate.opsForValue().set(key + "_file", url,
                                Duration.ofMinutes(20));
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
                                .fileUrl(url)
                                .build();
                        multimediaService.save(multimedia, record);
                    }
                    try {
                        log.info("开始保存文件数据到文件：{}", fileName);
                        FileUtils.writeByteArrayToFile(new File(fileName), b.getData(), true);
                    } catch (IOException e) {
                        log.error("文件保存错误: count = " + count + ", number = " + number, e);
                    }
                    Set<Object> keys = stringRedisTemplate.opsForHash().keys(key);
                    if (keys.size() == count) {
                        log.info("完成传输，合并文件！");
                        Set<Object> hasKeys = stringRedisTemplate.opsForHash().keys(key);
                        String target = stringRedisTemplate.opsForValue().get(key + "_file");
                        try (FileChannel outChannel = new FileOutputStream(target).getChannel()) {
                            List<File> files = hasKeys.stream().map(o -> ((String) o))
                                    .sorted((Comparator.comparing(Integer::valueOf)))
                                    .map(o -> {
                                        String s = (String) stringRedisTemplate.opsForHash().get(key, o);
                                        return new File(Objects.requireNonNull(s));
                                    }).collect(Collectors.toList());
                            for (File file : files) {
                                log.info("合并文件 :{}", file.getPath());
                                try (FileChannel inChannel = new FileInputStream(file).getChannel()) {
                                    outChannel.transferFrom(inChannel, outChannel.size(), inChannel.size());
                                }
                            }
                            for (File file : files) {
                                file.delete();
                            }
                            stringRedisTemplate.delete(key);
                            stringRedisTemplate.delete(key + "_file");
                            final MediaDataUploadMsg body = new MediaDataUploadMsg();
                            body.setId(b.getId());
                            final Header header = new Header(h.getSimNo(), false, EncryptionForm.NOTHING);
                            m2 = new Message();
                            m2.setHeader(header);
                            m2.setMsgBody(body);

                        } catch (FileNotFoundException e) {
                            log.error("FileNotFoundException", e);
                        } catch (IOException e) {
                            log.error("IOException", e);
                        }
                    }
                    return Mono.justOrEmpty(m2);
                });
    }
}

package com.avenger.jt808.handler;

import com.avenger.jt808.base.pbody.LocationAndAlarmMsg;
import com.avenger.jt808.base.pbody.LocationDataBatchMsg;
import com.avenger.jt808.domain.Header;
import com.avenger.jt808.domain.Message;
import com.avenger.jt808.domain.entity.GpsRecord;
import com.avenger.jt808.service.GpsRecordService;
import com.avenger.jt808.util.CommonMessageUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jg.wang on 2020/4/30.
 * Description: 处理终端批量上传位置数据
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class LocationDataBatchMsgHandler implements MessageHandler {


    @NonNull
    private final GpsRecordService gpsRecordService;

    @Override
    public short getId() {
        return 0x0704;
    }

    @Override
    public Publisher<Message> process(Message m) {
        return Mono.justOrEmpty(m)
                .filter(message -> message.getMsgBody() instanceof LocationDataBatchMsg)
                .map(message -> {
                    final Header h = message.getHeader();
                    final LocationDataBatchMsg b = (LocationDataBatchMsg) message.getMsgBody();
                    final List<LocationAndAlarmMsg> locationAndAlarmMsgs = b.getLocationAndAlarmMsgs();
                    if (!CollectionUtils.isEmpty(locationAndAlarmMsgs)) {
                        final List<GpsRecord> records = locationAndAlarmMsgs.stream()
                                .map(me -> LocationAndAlarmMsgHandler.fetch(h, me))
                                .collect(Collectors.toList());
                        gpsRecordService.saveAll(records);
                    }
                    return CommonMessageUtils.success(h);
                });
    }
}

package com.avenger.jt808.handler;

import com.avenger.jt808.base.pbody.*;
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

import java.math.BigDecimal;
import java.sql.Timestamp;
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
                                .map(me -> {
                                    final GpsRecord.GpsRecordBuilder gpsRecordBuilder = GpsRecord
                                            .builder()
                                            .time(Timestamp.valueOf(me.getTime()))
                                            .simNo(h.getSimNo())
                                            .longitude(BigDecimal.valueOf(me.getLongitude()).divide(BigDecimal.valueOf(1000000)).doubleValue())
                                            .latitude(BigDecimal.valueOf(me.getLatitude()).divide(BigDecimal.valueOf(1000000)).doubleValue())
                                            .altitude(me.getAltitude())
                                            .alarmFlag(me.getAlarmFlag())
                                            .vehicleStatus(me.getStatus())
                                            .speed(me.getSpeed() / 10)
                                            .direction((int) me.getDirection());
                                    me.getAdditionals().forEach(additional -> {
                                        switch (additional.getId()) {
                                            case 0x01:
                                                final MileageAdd mileageAdd = (MileageAdd) additional;
                                                gpsRecordBuilder.addMileage(mileageAdd.getMileage());
                                                break;
                                            case 0x02:
                                                final FuelQuantityAdd quantityAdd = (FuelQuantityAdd) additional;
                                                gpsRecordBuilder.addFuelQuantity((int) quantityAdd.getFuelQuantity());
                                                break;
                                            case 0x03:
                                                final TachographSpeedAdd speedAdd = (TachographSpeedAdd) additional;
                                                gpsRecordBuilder.addSpeed(speedAdd.getSpeed() / 10);
                                                break;
                                            case 0x14:
                                                final VideoAdd videoAdd = (VideoAdd) additional;
                                                gpsRecordBuilder.addVideo(videoAdd.getRaw());
                                                break;

                                            case 0x15:
                                                final LossOfVideoSignalAdd lossOfVideoSignalAdd = (LossOfVideoSignalAdd) additional;
                                                gpsRecordBuilder.addVideoLoss(lossOfVideoSignalAdd.getChannelId().stream().map(i -> "" + i).collect(Collectors.joining(",")));
                                                break;
                                            case 0x25:
                                                final StatusExtendAdd statusExtendAdd = (StatusExtendAdd) additional;
                                                gpsRecordBuilder.addVehicleStatus(statusExtendAdd.getStatus());
                                                break;
                                            case 0x2A:
                                                final IoStatusAdd ioStatusAdd = (IoStatusAdd) additional;
                                                gpsRecordBuilder.addAnalog((int) ioStatusAdd.getStatus());
                                                break;
                                            case 0x30:
                                                final SignalAdd signalAdd = (SignalAdd) additional;
                                                gpsRecordBuilder.addCellularSignal((int) signalAdd.getValue());
                                                break;
                                            case 0x31:
                                                final GnssSatelliteAdd satelliteAdd = (GnssSatelliteAdd) additional;
                                                gpsRecordBuilder.addSatellites(((int) satelliteAdd.getValue()));
                                                break;
                                            default:
                                                break;
                                        }
                                    });
                                    return gpsRecordBuilder.build();
                                })
                                .collect(Collectors.toList());
                        gpsRecordService.saveAll(records);
                    }
                    return CommonMessageUtils.success(h);
                });
    }
}

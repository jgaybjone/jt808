package com.avenger.jt808.handler;

import com.avenger.jt808.base.pbody.*;
import com.avenger.jt808.domain.Header;
import com.avenger.jt808.domain.Message;
import com.avenger.jt808.domain.entity.GpsRecord;
import com.avenger.jt808.service.GpsRecordService;
import com.avenger.jt808.util.CommonMessageUtils;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by jg.wang on 2020/4/29.
 * Description:
 */
@SuppressWarnings("BigDecimalMethodWithoutRoundingCalled")
@Component
@Slf4j
@AllArgsConstructor
public class LocationAndAlarmMsgHandler implements MessageHandler {

    @NonNull
    private final GpsRecordService gpsRecordService;

    @Override
    public short getId() {
        return 0x0200;
    }

    @Override
    public Publisher<Message> process(Message m) {
        return Mono.justOrEmpty(Optional.ofNullable(m)
                .filter(message -> message.getMsgBody() instanceof LocationAndAlarmMsg)
                .map(message -> {
                    final Header h = message.getHeader();
                    final LocationAndAlarmMsg b = (LocationAndAlarmMsg) message.getMsgBody();
                    final GpsRecord.GpsRecordBuilder gpsRecordBuilder = GpsRecord
                            .builder()
                            .time(Timestamp.valueOf(b.getTime()))
                            .simNo(h.getSimNo())
                            .longitude(BigDecimal.valueOf(b.getLongitude()).divide(BigDecimal.valueOf(1000000)))
                            .latitude(BigDecimal.valueOf(b.getLatitude()).divide(BigDecimal.valueOf(1000000)))
                            .altitude(b.getAltitude())
                            .alarmFlag(b.getAlarmFlag())
                            .vehicleStatus(b.getStatus())
                            .speed(b.getSpeed() / 10)
                            .direction((int) b.getDirection());
                    b.getAdditionals().forEach(additional -> {
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
                    gpsRecordService.saveEntity(gpsRecordBuilder.build());
                    // todo 确认报警待开发
                    return CommonMessageUtils.success(h);
                }));
    }
}

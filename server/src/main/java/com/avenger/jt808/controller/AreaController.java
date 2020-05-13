package com.avenger.jt808.controller;

import com.avenger.jt808.base.tbody.*;
import com.avenger.jt808.controller.vo.AreaBase;
import com.avenger.jt808.controller.vo.CircularAreaVO;
import com.avenger.jt808.controller.vo.PolygonAreaVO;
import com.avenger.jt808.controller.vo.RectAreaVO;
import com.avenger.jt808.domain.EncryptionForm;
import com.avenger.jt808.domain.Header;
import com.avenger.jt808.domain.Message;
import com.avenger.jt808.enums.RegionSettingType;
import com.avenger.jt808.server.TermConnManager;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jg.wang on 2020/5/9.
 * Description:
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/areas")
@Api(tags = "区域管理")
public class AreaController {

//    @NonNull
//    private final CircularAreaService circularAreaService;


//    @PostMapping
//    @ApiOperation(value = "增加圆形区域")
//    public Mono<CircularArea> add(@RequestBody CircularArea circularArea) {
//        return Mono.create(sink -> {
//            circularAreaService.saveEntity(circularArea);
//            sink.success(circularArea);
//        });
//    }
//
//    @PutMapping("/{id}")
//    @ApiOperation(value = "更新圆形区域")
//    public Mono<?> update(@PathVariable Integer id, @RequestBody CircularArea circularArea) {
//        return Mono.create(sink -> {
//            circularArea.setId(id);
//            circularAreaService.saveEntity(circularArea);
//            sink.success();
//        });
//    }
//
//    @DeleteMapping("/{id}")
//    @ApiOperation(value = "删除圆形区域")
//    public Mono<?> delete(@PathVariable Integer id) {
//        return Mono.create(sink -> circularAreaService.crudAndConsumer(rep -> {
//            final CircularArea circularArea = rep.findById(id)
//                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "圆形区域不存在，id=" + id));
//            rep.deleteById(circularArea.getId());
//        }));
//    }

    @PostMapping("/terminal/circular")
    @ApiOperation(value = "圆形区域下发到终端")
    public Mono<?> setCircularArea(@RequestBody @Validated List<SetAreaReq<CircularAreaVO>> req) {
        return Mono.create(sink -> sink.success(Collections.singletonMap("msg", "区域设置成功！"))).
                doOnSuccess(o -> req.forEach(set -> {
                    if (CollectionUtils.isEmpty(set.getCircularAreas())) {
                        set.setCircularAreas(Collections.emptyList());
                    }
                    final List<CircularRegionSettingMsg.CircularRegion> circularRegions = set.getCircularAreas().stream().map(circularArea -> {
                        final CircularRegionSettingMsg.CircularRegion circularRegion = new CircularRegionSettingMsg.CircularRegion();
                        circularRegion.setId(circularArea.getId());
                        circularRegion.setCenterLatitude((Double.valueOf(circularArea.getCentralLatitude() * 1000000)).intValue());
                        circularRegion.setCenterLongitude((Double.valueOf(circularArea.getCenterLongitude() * 1000000)).intValue());
                        circularRegion.setRadius(circularArea.getRadius());
                        this.setRegionCommon(circularArea, circularRegion);
                        return circularRegion;
                    }).collect(Collectors.toList());
                    final Header header = new Header(set.getSimNo(), false, EncryptionForm.NOTHING);
                    final Message message = new Message();
                    message.setHeader(header);
                    final CircularRegionSettingMsg circularRegionSettingMsg = new CircularRegionSettingMsg();
                    if (!CollectionUtils.isEmpty(circularRegions)) {
                        circularRegionSettingMsg.setCircularRegions(circularRegions);
                        circularRegionSettingMsg.setType(RegionSettingType.ADD);
                        message.setMsgBody(circularRegionSettingMsg);
                    } else {
                        final DeleteCircularRegionMsg deleteCircularRegionMsg = new DeleteCircularRegionMsg();
                        message.setMsgBody(deleteCircularRegionMsg);
                    }
                    TermConnManager.sendMessage(message);
                }));
    }

    @PostMapping("/terminal/rect")
    @ApiOperation(value = "矩形区域下发到终端")
    public Mono<?> setRectArea(@RequestBody @Validated List<SetAreaReq<RectAreaVO>> req) {
        return Mono.create(sink -> sink.success(Collections.singletonMap("msg", "区域设置成功！"))).
                doOnSuccess(o -> req.forEach(set -> {
                    if (CollectionUtils.isEmpty(set.getCircularAreas())) {
                        set.setCircularAreas(Collections.emptyList());
                    }
                    final List<RectangularRegionSettingMsg.RectangularRegion> rectangularRegions = set.getCircularAreas().stream().map(circularArea -> {
                        final RectangularRegionSettingMsg.RectangularRegion rectangularRegion = new RectangularRegionSettingMsg.RectangularRegion();
                        rectangularRegion.setLatitudeOfUpperLeftPoint((Double.valueOf(circularArea.getLatitudeOfUpperLeftPoint() * 1000000)).intValue());
                        rectangularRegion.setLatitudeOfUpperRightPoint((Double.valueOf(circularArea.getLatitudeOfUpperRightPoint() * 1000000)).intValue());
                        rectangularRegion.setLongitudeOfUpperLeftPoint((Double.valueOf(circularArea.getLongitudeOfUpperLeftPoint() * 1000000)).intValue());
                        rectangularRegion.setLongitudeOfUpperRightPoint((Double.valueOf(circularArea.getLongitudeOfUpperRightPoint() * 1000000)).intValue());
                        this.setRegionCommon(circularArea, rectangularRegion);
                        return rectangularRegion;
                    }).collect(Collectors.toList());
                    final Header header = new Header(set.getSimNo(), false, EncryptionForm.NOTHING);
                    final Message message = new Message();
                    message.setHeader(header);
                    if (!CollectionUtils.isEmpty(rectangularRegions)) {
                        final RectangularRegionSettingMsg rectangularRegionSettingMsg = new RectangularRegionSettingMsg();
                        rectangularRegionSettingMsg.setRectangularRegions(rectangularRegions);
                        rectangularRegionSettingMsg.setType(RegionSettingType.ADD);
                        message.setMsgBody(rectangularRegionSettingMsg);
                    } else {
                        final DeleteRectangularRegionMsg deleteCircularRegionMsg = new DeleteRectangularRegionMsg();
                        message.setMsgBody(deleteCircularRegionMsg);
                    }
                    TermConnManager.sendMessage(message);
                }));
    }

    @Data
    private static class SetAreaReq<T extends AreaBase> {
        @NotEmpty
        private String simNo;
        private List<T> circularAreas = Lists.newArrayList();
    }

    @PostMapping("/terminal/polygonal")
    @ApiOperation(value = "多边形区域下发到终端")
    public Mono<?> setPolygonalArea(@RequestBody @Validated SetPolygonal req) {
        return Mono.justOrEmpty(req).doOnNext(r -> {
            final PolygonAreaVO polygon = r.getPolygon();
            final String simNo = r.getSimNo();
            final List<PolygonalRegionSettingMsg.GeographicCoordinates> geographicCoordinates = polygon.getXies().stream().map(xy -> {
                final PolygonalRegionSettingMsg.GeographicCoordinates coordinates = new PolygonalRegionSettingMsg.GeographicCoordinates();
                coordinates.setLatitude(Double.valueOf(xy.getX() * 1000000).intValue());
                coordinates.setLongitude(Double.valueOf(xy.getY() * 1000000).intValue());
                return coordinates;
            }).collect(Collectors.toList());
            final PolygonalRegionSettingMsg polygonalRegionSettingMsg = new PolygonalRegionSettingMsg();
            this.setRegionCommon(polygon, polygonalRegionSettingMsg);
            polygonalRegionSettingMsg.setCoordinates(geographicCoordinates);
            final Header header = new Header(simNo, false, EncryptionForm.NOTHING);
            final Message message = new Message();
            message.setHeader(header);
            message.setMsgBody(polygonalRegionSettingMsg);
            TermConnManager.sendMessage(message);
        }).then();
    }

    @Data
    private static class SetPolygonal {
        @NotEmpty
        private String simNo;
        @NotNull
        private PolygonAreaVO polygon;
    }

    private void setRegionCommon(AreaBase areaBase, RegionCommon common) {
        common.setId(areaBase.getId());
        common.setStartTime(areaBase.getStartAt());
        common.setEndTime(areaBase.getEndAt());
        common.setSpeedLimit(areaBase.getTopSpeed().shortValue());
        common.setOverSpeedDuration(areaBase.getOverspeedDuration().byteValue());
        if (areaBase.getByTime()) {
            common.timeType();
        }
        if (areaBase.getSpeedLimit()) {
            common.speedType();
        }
        if (areaBase.getAlarmTheDriverEntering()) {
            common.alarmTheDriverWhenEnteringTheArea();
        }
        if (areaBase.getAlarmThePlatformEntering()) {
            common.alarmThePlatformWhenEnteringTheArea();
        }
        if (areaBase.getAlarmTheDriverOuting()) {
            common.alarmTheDriverWhenOutTheArea();
        }
        if (areaBase.getAlarmThePlatformOuting()) {
            common.alarmThePlatformWhenOutTheArea();
        }
        if (areaBase.getLatitudeDirection()) {
            common.southLatitudeType();
        } else {
            common.northLatitudeType();
        }
        if (areaBase.getLongitudeDirection()) {
            common.westLongitudeType();
        } else {
            common.eastLongitudeType();
        }
        if (areaBase.getOpenOrNot()) {
            common.doorOpeningProhibitedType();
        } else {
            common.doorOpeningAllowedType();
        }
        if (areaBase.getOpenCommunicationEntering()) {
            common.disableCommunicationModuleType();
        } else {
            common.enableCommunicationModuleType();
        }
        if (areaBase.getNotCollectGpsEntering()) {
            common.notCollectGnssType();
        } else {
            common.collectGnssType();
        }
    }

}

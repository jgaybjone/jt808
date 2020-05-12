package com.avenger.jt808.controller.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

/**
 * Created by jg.wang on 2020/5/12.
 * Description:
 */
@Data
public class AreaBase {
    @ApiModelProperty("区域id")
    @NotEmpty
    private Integer id;
    //    @Column(name = "by_time", nullable = false)
    @NotEmpty
    private Boolean byTime;
    //    @Column(name = "speed_limit", nullable = false)
    @NotEmpty
    private Boolean speedLimit;
    //    @Column(name = "alarm_the_driver_entering", nullable = false)
    @NotEmpty
    private Boolean alarmTheDriverEntering;
    //    @Column(name = "alarm_the_platform_entering", nullable = false)
    @NotEmpty
    private Boolean alarmThePlatformEntering;
    //    @Column(name = "alarm_the_driver_outing", nullable = false)
    @NotEmpty
    private Boolean alarmTheDriverOuting;
    //    @Column(name = "alarm_the_platform_outing", nullable = false)
    @NotEmpty
    private Boolean alarmThePlatformOuting;
    //    @Column(name = "latitude_direction", nullable = false)
    @NotEmpty
    private Boolean latitudeDirection;
    //    @Column(name = "longitude_direction", nullable = false)
    @NotEmpty
    private Boolean longitudeDirection;
    //    @Column(name = "open_or_not", nullable = false)
    @NotEmpty
    private Boolean openOrNot;
    //    @Column(name = "open_communication_entering", nullable = false)
    @NotEmpty
    private Boolean openCommunicationEntering;
    //    @Column(name = "not_collect_gps_entering", nullable = false)
    @NotEmpty
    private Boolean notCollectGpsEntering;
    //    @Column(name = "start_at", nullable = true)
    @NotEmpty
    private Timestamp startAt;
    //    @Column(name = "end_at", nullable = true)
    @NotEmpty
    private Timestamp endAt;
    //    @Column(name = "top_speed", nullable = true)
    @NotEmpty
    private Integer topSpeed;
    //    @Column(name = "overspeed_duration", nullable = true)
    @NotEmpty
    private Integer overspeedDuration;
}

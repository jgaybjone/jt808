package com.avenger.jt808.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by jg.wang on 2020/5/9.
 * Description:
 */
@Entity
@Table(name = "circular_area", schema = "public", catalog = "postgres")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class CircularArea implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "by_time", nullable = false)
    private boolean byTime;
    @Column(name = "speed_limit", nullable = false)
    private boolean speedLimit;
    @Column(name = "alarm_the_driver_entering", nullable = false)
    private boolean alarmTheDriverEntering;
    @Column(name = "alarm_the_platform_entering", nullable = false)
    private boolean alarmThePlatformEntering;
    @Column(name = "alarm_the_driver_outing", nullable = false)
    private boolean alarmTheDriverOuting;
    @Column(name = "alarm_the_platform_outing", nullable = false)
    private boolean alarmThePlatformOuting;
    @Column(name = "latitude_direction", nullable = false)
    private boolean latitudeDirection;
    @Column(name = "longitude_direction", nullable = false)
    private boolean longitudeDirection;
    @Column(name = "open_or_not", nullable = false)
    private boolean openOrNot;
    @Column(name = "open_communication_entering", nullable = false)
    private boolean openCommunicationEntering;
    @Column(name = "not_collect_gps_entering", nullable = false)
    private boolean notCollectGpsEntering;
    @Column(name = "central_latitude", nullable = false, precision = 0)
    private double centralLatitude;
    @Column(name = "center_longitude", nullable = false, precision = 0)
    private double centerLongitude;
    @Column(name = "radius", nullable = false)
    private int radius;
    @Column(name = "start_at", nullable = true)
    private Timestamp startAt;
    @Column(name = "end_at", nullable = true)
    private Timestamp endAt;
    @Column(name = "top_speed", nullable = true)
    private Integer topSpeed;
    @Column(name = "overspeed_duration", nullable = true)
    private Integer overspeedDuration;
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    @Column(name = "company_id", length = 40)
    private String companyId;
}

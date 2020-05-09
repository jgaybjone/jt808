package com.avenger.jt808.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by jg.wang on 2020/4/29.
 * Description:
 */
@Entity
@Table(name = "gps_record", schema = "public", catalog = "postgres")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class GpsRecord implements Serializable {
    @Id
    @Column(name = "time", nullable = false)
    private Timestamp time;
    @Column(name = "sim_no", nullable = true, length = 20)
    private String simNo;
    @Column(name = "longitude", nullable = true, precision = 2)
    private Double longitude;
    @Column(name = "latitude", nullable = true, precision = 2)
    private Double latitude;
    @Column(name = "alarm_flag", nullable = true)
    private Integer alarmFlag;
    @Column(name = "vehicle_status", nullable = true)
    private Integer vehicleStatus;
    @Column(name = "altitude", nullable = true)
    private Integer altitude;
    @Column(name = "speed", nullable = true)
    private Integer speed;
    @Column(name = "direction", nullable = true)
    private Integer direction;
    @Column(name = "add_mileage", nullable = true)
    private Integer addMileage;
    @Column(name = "add_fuel_quantity", nullable = true)
    private Integer addFuelQuantity;
    @Column(name = "add_speed", nullable = true)
    private Integer addSpeed;
    @Column(name = "add_video", nullable = true)
    private Integer addVideo;
    @Column(name = "add_video_loss", nullable = true, length = 30)
    private String addVideoLoss;
    @Column(name = "add_vehicle_status", nullable = true)
    private Integer addVehicleStatus;
    @Column(name = "add_analog", nullable = true)
    private Integer addAnalog;
    @Column(name = "add_cellular_signal", nullable = true)
    private Integer addCellularSignal;
    @Column(name = "add_satellites", nullable = true)
    private Integer addSatellites;

}

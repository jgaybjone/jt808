package com.avenger.jt808.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by jg.wang on 2020/4/27.
 * Description:
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class Terminal implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "snowflake-id")
    @GenericGenerator(name = "snowflake-id", strategy = "com.avenger.jt808.util.SnowflakeIdGen")
    private Long id;
    @Column(name = "province")
    private Short province;
    @Column(name = "city")
    private Short city;
    @Column(name = "manufacturer_code", length = 5)
    private String manufacturerCode;
    @Column(name = "model", length = 20)
    private String model;
    @Column(name = "term_id", nullable = false, length = 7)
    private String termId;
    @Column(name = "color")
    private Short color;
    @Column(name = "vehicle_ident", length = 50)
    private String vehicleIdent;
    @Column(name = "auth_code", length = 50)
    private String authCode;
    @Column(name = "sim_no")
    private String simNo;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    @Generated(GenerationTime.INSERT)
    private Timestamp createdAt;
    @Column(name = "company_id", length = 40)
    private String companyId;
}

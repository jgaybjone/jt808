package com.avenger.jt808.controller.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Created by jg.wang on 2020/5/9.
 * Description:
 */
//@Entity
//@Table(name = "circular_area", schema = "public", catalog = "postgres")
@EqualsAndHashCode(callSuper = true)
@Data
public class CircularAreaVO extends AreaBase implements Serializable {
    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
    //    @Column(name = "central_latitude", nullable = false, precision = 0)
    @NotEmpty
    @ApiModelProperty("中心纬度")
    private Double centralLatitude;
    //    @Column(name = "center_longitude", nullable = false, precision = 0)
    @NotEmpty
    @ApiModelProperty("中心经度")
    private Double centerLongitude;
    //    @Column(name = "radius", nullable = false)
    @NotEmpty
    @ApiModelProperty("半径（米）")
    private Integer radius;

//    @Column(name = "created_at", nullable = true)
//    private Timestamp createdAt;
//    @Column(name = "company_id", length = 40)
//    private String companyId;
//    @Version
//    @Column(name = "version")
//    private Integer version;
}

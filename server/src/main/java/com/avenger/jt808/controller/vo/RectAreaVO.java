package com.avenger.jt808.controller.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by jg.wang on 2020/5/12.
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RectAreaVO extends AreaBase {
    /**
     * 左上点纬度
     */
    private Double latitudeOfUpperLeftPoint;
    /**
     * 左上点经度
     */
    private Double longitudeOfUpperLeftPoint;
    /**
     * 左上点纬度
     */
    private Double latitudeOfUpperRightPoint;
    /**
     * 左上点经度
     */
    private Double longitudeOfUpperRightPoint;
}

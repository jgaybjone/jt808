package com.avenger.jt808.controller.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by jg.wang on 2020/5/12.
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PolygonAreaVO extends AreaBase {

    @Valid
    @NotEmpty
    private List<XY> xies;

    @Data
    public static class XY {
        @NotNull
        private Double x;
        @NotNull
        private Double y;
    }
}

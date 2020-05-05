package com.avenger.jt808.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jg.wang on 2020/5/4.
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseMsgVO {
    private String message;
    private String detail;
    private String error;
}

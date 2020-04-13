package com.avenger.jt808.base;

import lombok.Data;

/**
 * Created by jg.wang on 2020/4/10.
 * Description:
 */
@Data
public class StringSettingParams implements SettingParams<String> {
    private int id;
    private String param;
}

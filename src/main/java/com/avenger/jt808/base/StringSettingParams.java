package com.avenger.jt808.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jg.wang on 2020/4/10.
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StringSettingParams implements SettingParams<String> {
    private int id;
    private String param;
}

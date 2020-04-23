package com.avenger.jt808.base;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/10.
 * Description:
 */
@Data
@AllArgsConstructor
public class ByteSettingParams implements SettingParams<Byte> {
    private int id;
    private Byte param;
}

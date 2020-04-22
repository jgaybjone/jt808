package com.avenger.jt808.base;

import lombok.Data;

/**
 * Created by jg.wang on 2020/4/13.
 * Description:
 */
@Data
public class BytesSettingParams implements SettingParams<byte[]> {

    private int id;

    private byte[] param;
}

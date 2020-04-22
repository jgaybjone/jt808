package com.avenger.jt808.base;

import lombok.Data;

/**
 * Created by jg.wang on 2020/4/9.
 * Description:
 */
@Data
public class Message {

    private Header header;

    private Body msgBody;
    /**
     * 校验结果
     */
    private boolean verified;

}

package com.avenger.jt808.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by jg.wang on 2020/4/9.
 * Description:
 */
@Data
public class Message implements Serializable {

    private Header header;

    private Body msgBody;
    /**
     * 校验结果
     */
    private boolean verified;

}

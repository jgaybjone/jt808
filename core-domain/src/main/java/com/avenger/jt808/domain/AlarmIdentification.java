package com.avenger.jt808.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by jg.wang on 2019/12/14.
 * Description:
 */
@Data
public class AlarmIdentification {
    private String terminalId;
    private LocalDateTime date;
    private byte serialNumber;
    private byte numberOfAttachments;
    private byte reserve;
}

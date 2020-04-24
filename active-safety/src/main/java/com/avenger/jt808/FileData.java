package com.avenger.jt808;

import lombok.Data;

/**
 * Created by jg.wang on 2020/4/24.
 * Description:
 */
@Data
public class FileData {
    private String fileName;
    private long offset;
    private byte[] data;
}

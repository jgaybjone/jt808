package com.avenger.jt808.enums;

/**
 * Created by jg.wang on 2020/4/12.
 * Description:
 */
public enum IcReadResult {
    /**
     * 0x00:IC 卡读卡成功;
     */
    SUCCESS,
    /**
     * 0x01:读卡失败，原因为卡片密钥认证未通过;
     */
    READ_FAIL1,
    /**
     * 0x02:读卡失败，原因为卡片已被锁定;
     */
    READ_FAIL2,
    /**
     * 0x03:读卡失败，原因为卡片被拔出;
     */
    READ_FAIL3,
    /**
     * 0x04:读卡失败，原因为数据校验错误。
     */
    READ_FAIL4;


    public static IcReadResult valueOf(byte v) {
        switch (v) {
            case 0:
                return SUCCESS;
            case 1:
                return READ_FAIL1;
            case 2:
                return READ_FAIL2;
            case 3:
                return READ_FAIL3;
            default:
                return READ_FAIL4;
        }
    }


}

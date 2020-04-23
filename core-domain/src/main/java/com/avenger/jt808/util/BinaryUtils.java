package com.avenger.jt808.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

/**
 * Created by jg.wang on 2020/3/31.
 * Description:
 */
@UtilityClass
public class BinaryUtils {

    /**
     * @param num   数字
     * @param index 二进制的第index位
     * @return 是否位1
     */
    public static boolean bit(int num, int index) {
        final int i = 1 << index - 1;
        final int j = num & i;
        return j > 0;
    }

    /**
     * @param num   数字
     * @param value 二进制字符串
     * @return 是否位1
     */
    public static boolean bit(int num, String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        final int index = (int) Long.parseLong(value, 2);
        final int j = num & index;
        return j > 0;
    }

    /**
     * @param num   数字
     * @param index 二进制的第index位
     * @return 是否位1
     */
    public static boolean bit(byte num, int index) {
        final int i = 1 << index - 1;
        final int j = num & i;
        return j > 0;
    }

    public static boolean bit(byte num, String value) {
        final int index = Integer.parseInt(value, 2);
        final int j = num & index;
        return j > 0;
    }
}

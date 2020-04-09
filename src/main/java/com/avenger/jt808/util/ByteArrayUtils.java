package com.avenger.jt808.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.nio.ByteBuffer;

/**
 * Created by jg.wang on 2020/4/9.
 * Description:
 */
@UtilityClass
public class ByteArrayUtils {

    public static String toBcdString(byte[] bytes) {
        StringBuilder bcd = new StringBuilder();
        for (byte aByte : bytes) {
            bcd.append(String.format("%02X", aByte));
        }
        return bcd.toString();
    }

    public static byte[] bcdStrToBytes(String bcd) {
        bcd = StringUtils.isEmpty(bcd) ? "0" : bcd;
        ByteBuffer bf = ByteBuffer.allocate(bcd.length() / 2);
        for (int i = 0; i < bcd.length(); i++) {
            String hexStr = bcd.charAt(i) + "";
            i++;
            hexStr += bcd.charAt(i);
            byte b = (byte) Integer.parseInt(hexStr, 16);
            bf.put(b);
        }
        return bf.array();
    }

}

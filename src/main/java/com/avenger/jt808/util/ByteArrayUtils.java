package com.avenger.jt808.util;

import io.netty.buffer.ByteBuf;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;

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

    public static String toBcdString(ByteBuf buf, int len) {
        final byte[] bytes = new byte[len];
        buf.readBytes(bytes);
        return toBcdString(bytes);
    }

    public static LocalDateTime bcdToDate(ByteBuf buf, int len) {
        final byte[] bytes = new byte[len];
        buf.readBytes(bytes);
        final String s = "20" + toBcdString(bytes);
        final String str = s.replace("-", "").replace(":", "");
        if (str.length() == 8) {
            return LocalDateTimeUtils.parse("yyyyMMdd", str);
        }
        return LocalDateTimeUtils.parse("yyyyMMddHHmmss", str);
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

package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.enums.MenuSettingType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
@WritingMessageType(type = ((byte) 0x8303))
@Data
public class MenuSettingMsg implements Body {

    private MenuSettingType type;

    private List<Menu> menus = Collections.emptyList();

    @Override
    public byte[] serialize() {
        final ByteBuf byteBuf = Unpooled.buffer(300).writeByte(type.getValue());
        if (!CollectionUtils.isEmpty(menus)) {
            menus.forEach(menu -> {
                byteBuf
                        .writeByte(menu.type)
                        .writeByte(menu.name.length())
                        .writeBytes(menu.name.getBytes(Charset.forName("GBK")));
            });
        }
        return byteBuf.array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }

    @Data
    public static class Menu {
        private byte type;
        private String name;
    }
}

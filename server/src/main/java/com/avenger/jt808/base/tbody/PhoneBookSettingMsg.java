package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.enums.ContactFlag;
import com.avenger.jt808.enums.PhoneBookSettingType;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
@WritingMessageType(type = ((byte) 0x8401))
@Data
public class PhoneBookSettingMsg implements Body {

    private PhoneBookSettingType type;

    private List<Contact> contacts = Collections.emptyList();


    @Override
    public byte[] serialize() {
        final ByteBuf byteBuf = Unpooled.buffer(300)
                .writeByte(type.ordinal())
                .writeByte(contacts.size());

        contacts.forEach(contact -> byteBuf.writeByte(contact.flag.getValue())
                .writeByte(contact.simNo.length())
                .writeBytes(contact.simNo.getBytes(Charset.forName("GBK")))
                .writeByte(contact.name.length())
                .writeBytes(contact.name.getBytes(Charset.forName("GBK"))));
        return ByteBufUtils.array(byteBuf);
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }

    @Data
    public static class Contact {
        /**
         * 1:呼入;2:呼出;3:呼入/呼出
         */
        private ContactFlag flag;

        private String simNo;

        private String name;


    }
}

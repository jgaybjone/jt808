package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.base.SettingParams;
import com.avenger.jt808.base.pbody.SettingDetailMsg;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/9.
 * Description:
 */
@SuppressWarnings({"rawtypes", "MismatchedQueryAndUpdateOfCollection"})
@WritingMessageType(type = (short) 0x8103)
public class PutSettingMsg implements Body {


    @Getter
    @Setter
    private List<SettingParams> settingParams = Collections.emptyList();

    public int getSize() {
        return settingParams.size();
    }

    @Override
    public byte[] serialize() {
        final ByteBuf buffer = Unpooled.buffer(200);
        buffer.writeByte(this.getSize());
        SettingDetailMsg.paramsSerialize(this.settingParams, buffer);
        return ByteBufUtils.array(buffer);
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.settingParams = SettingDetailMsg.params(byteBuf);
    }
}

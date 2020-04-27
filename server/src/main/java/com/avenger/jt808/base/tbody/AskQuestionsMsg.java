package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
@WritingMessageType(type = ((byte) 0x8302))
public class AskQuestionsMsg implements Body {

    private byte flag = 0;

    @Setter
    @Getter
    private String question;

    private List<Answer> answers = Collections.emptyList();

    /**
     * 紧急
     */
    public void setEmergency() {
        flag = (byte) (flag | 0b1);
    }

    /**
     * 终端 TTS 播读
     */
    public void setTtsPlay() {
        flag = (byte) (flag | 0b1000);
    }

    /**
     * 广告屏显示
     */
    public void setAd() {
        flag = (byte) (flag | 0b10000);
    }

    @Override
    public byte[] serialize() {
        final ByteBuf byteBuf = Unpooled.buffer(300).writeByte(flag)
                .writeByte(question.length())
                .writeBytes(question.getBytes(Charset.forName("GBK")));
        if (!CollectionUtils.isEmpty(answers)) {
            answers.forEach(answer -> {
                byteBuf.writeByte(answer.getId())
                        .writeByte(answer.content.length())
                        .writeBytes(answer.content.getBytes(Charset.forName("GBK")));
            });
        }
        return ByteBufUtils.array(byteBuf);
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }

    @Data
    public static class Answer {
        /**
         * 答案ID
         */
        private byte id;
        /**
         * 答案内容
         */
        private String content;
    }
}

package com.avenger.jt808.service;

import com.avenger.jt808.domain.Message;
import com.avenger.jt808.domain.entity.MessageRecord;
import com.avenger.jt808.domain.repository.MessageRecordRepository;
import org.springframework.context.ApplicationEvent;
import reactor.core.publisher.Flux;

/**
 * Created by jg.wang on 2020/5/6.
 * Description:
 */
public interface MessageRecordService extends BaseService<MessageRecord, Long, MessageRecordRepository> {

    Flux<?> sendCheck(String simNo, short serialNo);

    void responseMessage(RespMessageRecordEvent respMessageRecordEvent);

    void newMessage(NewMessageRecordEvent respMessageRecordEvent);

    class RespMessageRecordEvent extends ApplicationEvent {

        /**
         * Create a new {@code ApplicationEvent}.
         *
         * @param source the object on which the event initially occurred or with
         *               which the event is associated (never {@code null})
         */
        public RespMessageRecordEvent(Message source) {
            super(source);
        }
    }

    class NewMessageRecordEvent extends ApplicationEvent {

        /**
         * Create a new {@code ApplicationEvent}.
         *
         * @param source the object on which the event initially occurred or with
         *               which the event is associated (never {@code null})
         */
        public NewMessageRecordEvent(Message source) {
            super(source);
        }
    }
}

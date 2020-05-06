package com.avenger.jt808.service;

import com.avenger.jt808.domain.entity.MessageRecord;
import com.avenger.jt808.domain.repository.MessageRecordRepository;
import reactor.core.publisher.Flux;

/**
 * Created by jg.wang on 2020/5/6.
 * Description:
 */
public interface MessageRecordService extends BaseService<MessageRecord, Long, MessageRecordRepository> {

    Flux<?> sendCheck(String simNo, short serialNo);

}

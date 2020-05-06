package com.avenger.jt808.service.impl;

import com.avenger.jt808.domain.repository.MessageRecordRepository;
import com.avenger.jt808.service.MessageRecordService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by jg.wang on 2020/5/6.
 * Description:
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MessageRecordServiceImpl implements MessageRecordService {

    @NonNull
    private final MessageRecordRepository messageRecordRepository;

    @Override
    public MessageRecordRepository getRepository() {
        return this.messageRecordRepository;
    }
}

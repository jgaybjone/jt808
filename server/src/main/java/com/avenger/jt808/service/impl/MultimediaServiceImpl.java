package com.avenger.jt808.service.impl;

import com.avenger.jt808.domain.entity.GpsRecord;
import com.avenger.jt808.domain.entity.Multimedia;
import com.avenger.jt808.domain.repository.GpsRecordRepository;
import com.avenger.jt808.domain.repository.MultimediaRepository;
import com.avenger.jt808.service.MultimediaService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jg.wang on 2020/4/30.
 * Description:
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MultimediaServiceImpl implements MultimediaService {

    @NonNull
    private final MultimediaRepository multimediaRepository;
    @NonNull
    private final GpsRecordRepository gpsRecordRepository;

    @Override
    public MultimediaRepository getRepository() {
        return multimediaRepository;
    }

    @Override
    @Transactional
    public void save(Multimedia multimedia, GpsRecord record) {
        multimediaRepository.save(multimedia);
        gpsRecordRepository.save(record);
    }
}

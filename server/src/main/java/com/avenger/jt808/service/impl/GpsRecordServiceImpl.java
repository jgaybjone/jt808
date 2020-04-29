package com.avenger.jt808.service.impl;

import com.avenger.jt808.domain.repository.GpsRecordRepository;
import com.avenger.jt808.service.GpsRecordService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by jg.wang on 2020/4/29.
 * Description:
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GpsRecordServiceImpl implements GpsRecordService {
    @NonNull
    private final GpsRecordRepository gpsRecordRepository;

    @Override
    public GpsRecordRepository getRepository() {
        return gpsRecordRepository;
    }
}

package com.avenger.jt808.service;

import com.avenger.jt808.domain.entity.GpsRecord;
import com.avenger.jt808.domain.repository.GpsRecordRepository;

import java.sql.Timestamp;

/**
 * Created by jg.wang on 2020/4/29.
 * Description:
 */
public interface GpsRecordService extends BaseService<GpsRecord, Timestamp, GpsRecordRepository> {
}

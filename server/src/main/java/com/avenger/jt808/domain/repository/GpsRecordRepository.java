package com.avenger.jt808.domain.repository;

import com.avenger.jt808.domain.entity.GpsRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;

/**
 * Created by jg.wang on 2020/4/29.
 * Description:
 */
public interface GpsRecordRepository extends JpaRepository<GpsRecord, Timestamp> {
}

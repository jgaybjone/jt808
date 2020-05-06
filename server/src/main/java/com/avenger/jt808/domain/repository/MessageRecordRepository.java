package com.avenger.jt808.domain.repository;

import com.avenger.jt808.domain.entity.MessageRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by jg.wang on 2020/5/6.
 * Description:
 */
public interface MessageRecordRepository extends JpaRepository<MessageRecord, Long>, JpaSpecificationExecutor<MessageRecord> {
}

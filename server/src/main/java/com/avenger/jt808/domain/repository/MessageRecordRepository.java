package com.avenger.jt808.domain.repository;

import com.avenger.jt808.domain.entity.MessageRecord;
import com.avenger.jt808.enums.MessageRecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by jg.wang on 2020/5/6.
 * Description:
 */
public interface MessageRecordRepository extends JpaRepository<MessageRecord, Long>, JpaSpecificationExecutor<MessageRecord> {

    @Modifying
    @Query(value = "update MessageRecord m set m.status = :status where m.simNo = :simNo and m.serialNo = :serialNo and m.messageType = :messageType")
    void updateStatusBySerialNoAndSimNo(MessageRecordStatus status, String simNo, Integer serialNo, Integer messageType);

}

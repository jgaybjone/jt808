package com.avenger.jt808.domain.repository;

import com.avenger.jt808.domain.entity.TerminalSetting;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jg.wang on 2020/4/29.
 * Description:
 */
public interface TerminalSettingRepository extends JpaRepository<TerminalSetting, Long> {
    void deleteBySimNo(String simNo);
}

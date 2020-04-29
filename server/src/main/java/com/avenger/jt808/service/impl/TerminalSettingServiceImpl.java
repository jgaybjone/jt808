package com.avenger.jt808.service.impl;

import com.avenger.jt808.domain.repository.TerminalSettingRepository;
import com.avenger.jt808.service.TerminalSettingService;
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
public class TerminalSettingServiceImpl implements TerminalSettingService {

    @NonNull
    private TerminalSettingRepository terminalSettingRepository;

    @Override
    public TerminalSettingRepository getRepository() {
        return this.terminalSettingRepository;
    }
}

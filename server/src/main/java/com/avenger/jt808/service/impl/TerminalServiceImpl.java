package com.avenger.jt808.service.impl;

import com.avenger.jt808.domain.entity.Terminal;
import com.avenger.jt808.domain.repository.TerminalRepository;
import com.avenger.jt808.service.TerminalService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * Created by jg.wang on 2020/4/27.
 * Description:
 */
@Service
@RequiredArgsConstructor
public class TerminalServiceImpl implements TerminalService {

    @NonNull
    private final TerminalRepository terminalRepository;

    @Override
    public TerminalRepository getRepository() {
        return terminalRepository;
    }


    @Override
    @Transactional
    public boolean register(Terminal terminal) {
        if (StringUtils.isEmpty(terminal.getTermId())) {
            return false;
        }
        final Example<Terminal> example = Example.of(Terminal.builder().termId(terminal.getTermId()).build());
        return terminalRepository.findOne(example)
                .map(obj -> false)
                .orElseGet(() -> {
                    terminal.setAuthCode(UUID.randomUUID().toString());
                    terminalRepository.save(terminal);
                    return true;
                });
    }
}

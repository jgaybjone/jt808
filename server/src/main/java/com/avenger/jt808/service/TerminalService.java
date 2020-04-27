package com.avenger.jt808.service;

import com.avenger.jt808.domain.entity.Terminal;
import com.avenger.jt808.domain.repository.TerminalRepository;

/**
 * Created by jg.wang on 2020/4/27.
 * Description:
 */
public interface TerminalService extends BaseService<Terminal, Long, TerminalRepository> {

    boolean register(Terminal terminal);
}

package com.avenger.jt808.service.impl;

import com.avenger.jt808.domain.repository.CircularAreaRepository;
import com.avenger.jt808.service.CircularAreaService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by jg.wang on 2020/5/9.
 * Description:
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CircularAreaServiceImpl implements CircularAreaService {

    @NonNull
    private final CircularAreaRepository circularAreaRepository;

    @Override
    public CircularAreaRepository getRepository() {
        return this.circularAreaRepository;
    }
}

package com.avenger.jt808.service.impl;

import com.avenger.jt808.domain.repository.MultimediaEventRepository;
import com.avenger.jt808.service.MultimediaEventService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by jg.wang on 2020/4/30.
 * Description:
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MultimediaEventServiceImpl implements MultimediaEventService {

    @NonNull
    private final MultimediaEventRepository multimediaEventRepository;

    @Override
    public MultimediaEventRepository getRepository() {
        return multimediaEventRepository;
    }

}

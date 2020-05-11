package com.avenger.jt808.controller;

import com.avenger.jt808.service.CircularAreaService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by jg.wang on 2020/5/9.
 * Description:
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/area")
public class AreaController {

    @NonNull
    private final CircularAreaService circularAreaService;




}

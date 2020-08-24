package com.avenger.jt808.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by jg.wang on 2020/5/14.
 * Description:
 */
@RestController
public class IndexController {

    @RequestMapping({"/", ""})
    public Mono<String> index() {
        return Mono.justOrEmpty("Hello World!");
    }
}

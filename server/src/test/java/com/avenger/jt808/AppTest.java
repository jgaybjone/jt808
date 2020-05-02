package com.avenger.jt808;

import com.avenger.jt808.base.tbody.ShootAtOnceMsg;
import com.avenger.jt808.enums.ResolutionRatio;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by jg.wang on 2020/4/30.
 * Description:
 */
@SpringBootTest
@Slf4j
public class AppTest {

    public AppTest() {

    }

    RSocketRequester rSocketRequester;

    @Autowired
    RSocketStrategies rSocketStrategies;

    @Autowired
    RSocketRequester.Builder rsocketRequesterBuilder;

    @PostConstruct
    public void init() {
        this.rSocketRequester = rsocketRequesterBuilder.connectTcp("121.40.163.240", 8889).block();
    }


    @Test
    public void socket() throws InterruptedException {
        if (rSocketRequester != null) {
            ShootAtOnceMsg shootAtOnceMsg = new ShootAtOnceMsg();
            shootAtOnceMsg.setChannelId(((byte) 2));
            shootAtOnceMsg.setShootingTime(((short) 10));
            shootAtOnceMsg.setRealTimeUpload(true);
//            shootAtOnceMsg.setNumber(((short) 0xFFFF));
            shootAtOnceMsg.setNumber(((short) 3));
            shootAtOnceMsg.setResolutionRatio(ResolutionRatio.SVGA);
            shootAtOnceMsg.setVideoQuality(((byte) 5));
            shootAtOnceMsg.setBrightness(((byte) 125));
            shootAtOnceMsg.setContrastRatio((byte) 125);
            shootAtOnceMsg.setSaturation((byte) 125);
            shootAtOnceMsg.setChroma((byte) 125);
            HashMap<String, Object> params = new HashMap<>();
            params.put("simNo", "018168953558");
            params.put("body", shootAtOnceMsg);
            rSocketRequester.route("shoot").data(params).retrieveMono(Void.class).subscribe(aVoid -> log.info("finish"));
            TimeUnit.SECONDS.sleep(3);
        }
    }
}

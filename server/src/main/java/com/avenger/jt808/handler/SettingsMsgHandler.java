package com.avenger.jt808.handler;

import com.avenger.jt808.base.SettingParams;
import com.avenger.jt808.base.pbody.SettingDetailMsg;
import com.avenger.jt808.domain.Header;
import com.avenger.jt808.domain.Message;
import com.avenger.jt808.domain.entity.TerminalSetting;
import com.avenger.jt808.service.TerminalSettingService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by jg.wang on 2020/4/29.
 * Description:
 */
@SuppressWarnings("rawtypes")
@Component
@RequiredArgsConstructor
public class SettingsMsgHandler implements MessageHandler {

    @NonNull
    private final TerminalSettingService terminalSettingService;

    @Override
    public short getId() {
        return 0x0104;
    }

    @Override
    public Publisher<Message> process(Message m) {
        return Mono.justOrEmpty(
                Optional.ofNullable(m)
                        .filter(message -> message.getMsgBody() instanceof SettingDetailMsg)
                        .map(message -> {
                            final Header h = message.getHeader();
                            final SettingDetailMsg b = (SettingDetailMsg) message.getMsgBody();
                            final List<SettingParams> settingParams = b.getSettingParams();
                            if (!CollectionUtils.isEmpty(settingParams)) {
                                terminalSettingService.crudAndConsumer(repository -> {
                                    if (!repository.existsBySimNo(h.getSimNo())) {
                                        final List<TerminalSetting> terminalSettings = settingParams.stream()
                                                .map(set -> TerminalSetting.builder()
                                                        .simNo(h.getSimNo())
                                                        .settingId(set.getId())
                                                        .value(String.valueOf(set.getParam()).replaceAll("\0", ""))
                                                        .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                                                        .build())
                                                .collect(Collectors.toList());
                                        repository.saveAll(terminalSettings);
                                    }
                                });
                            }
                            return null;
                        })
        );
    }
}

package com.avenger.jt808.service.impl;

import com.avenger.jt808.domain.entity.MessageRecord;
import com.avenger.jt808.domain.repository.MessageRecordRepository;
import com.avenger.jt808.enums.MessageFlow;
import com.avenger.jt808.enums.MessageRecordStatus;
import com.avenger.jt808.service.MessageRecordService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import javax.persistence.criteria.Predicate;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jg.wang on 2020/5/6.
 * Description:
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MessageRecordServiceImpl implements MessageRecordService {

    @NonNull
    private final MessageRecordRepository messageRecordRepository;

    @Override
    public MessageRecordRepository getRepository() {
        return this.messageRecordRepository;
    }

    /**
     * @param simNo    终端手机号号码
     * @param serialNo 流水号
     * @return 定时触发检查发送状态
     */
    @Override
    public Flux<?> sendCheck(String simNo, short serialNo) {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        return Flux.interval(Duration.ofMillis(500)).zipWith(Flux.generate(fluxSink -> {
            if (atomicInteger.incrementAndGet() >= 100) {
                fluxSink.error(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "发送失败"));
                return;
            }
            final Optional<MessageRecord> one = messageRecordRepository.findOne((Specification<MessageRecord>) (root, query, criteriaBuilder) -> {
                final LocalDateTime now = LocalDateTime.now();

                List<Predicate> predicates = new ArrayList<>(4);
                predicates.add(criteriaBuilder.equal(root.get("simNo"), simNo));
                predicates.add(criteriaBuilder.equal(root.get("serialNo"), serialNo));
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), now));
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("flowTo"), MessageFlow.SEND));
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), now.minusMinutes(20)));
                return query
                        .where(predicates.toArray(new Predicate[0])).getRestriction();
            });
            one.ifPresent(messageRecord -> {
                if (messageRecord.getStatus() == MessageRecordStatus.RESPONDED) {
//                    fluxSink.next(Collections.singletonMap("message", "终端已回复"));
                    fluxSink.next(Collections.singletonMap("message", messageRecord.getResponse()));
                    fluxSink.complete();
                } else {
                    fluxSink.next(Collections.singletonMap("message", "已发送，等待终端回复"));
                }
            });
            if (!one.isPresent()) {
                fluxSink.next(Collections.singletonMap("message", "发送中"));
            }
        })).map(Tuple2::getT2);
    }
}

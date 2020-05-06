package com.avenger.jt808.service.impl;

import com.avenger.jt808.domain.entity.MessageRecord;
import com.avenger.jt808.domain.repository.MessageRecordRepository;
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

import javax.persistence.criteria.Predicate;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        return Flux.interval(Duration.ofMillis(200)).zipWith(Flux.create(fluxSink -> {
            final Optional<MessageRecord> one = messageRecordRepository.findOne((Specification<MessageRecord>) (root, query, criteriaBuilder) -> {
                final LocalDateTime now = LocalDateTime.now();

                List<Predicate> predicates = new ArrayList<>(4);
                predicates.add(criteriaBuilder.equal(root.get("simNo"), simNo));
                predicates.add(criteriaBuilder.equal(root.get("serialNo"), serialNo));
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), now));
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), now.minusMinutes(20)));
                return query
                        .where(predicates.toArray(new Predicate[0])).getRestriction();
            });
            one.ifPresent(messageRecord -> {
                if (messageRecord.getStatus() == MessageRecordStatus.RESPONDED) {
                    fluxSink.complete();
                } else {
                    fluxSink.next(Collections.singletonMap("message", "发送中"));
                }
            });
            if (!one.isPresent()) {
                fluxSink.error(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "发送失败"));
            }
        }));
    }
}

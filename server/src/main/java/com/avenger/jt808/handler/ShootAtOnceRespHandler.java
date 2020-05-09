package com.avenger.jt808.handler;

import com.avenger.jt808.base.pbody.ShootAtOnceRespMsg;
import com.avenger.jt808.domain.Message;
import com.avenger.jt808.service.MessageRecordService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Created by jg.wang on 2020/5/2.
 * Description: 摄像头立即拍摄命令应答处理
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Component
@Slf4j
@RequiredArgsConstructor
public class ShootAtOnceRespHandler implements MessageHandler {

    @NonNull
    private final ReactiveRedisTemplate reactiveRedisTemplate;
    @NonNull
    private final MessageRecordService messageRecordService;

    @Override
    public short getId() {
        return 0x0805;
    }

    @Override
    public Publisher<Message> process(Message message) {
        return Mono.<Message>empty()
                .doOnSuccess(v -> Optional.of(message)
                        .filter(m -> m.getMsgBody() instanceof ShootAtOnceRespMsg)
                        .ifPresent(m -> {
//                            final Header h = m.getHeader();
//                            final ShootAtOnceRespMsg b = (ShootAtOnceRespMsg) m.getMsgBody();
//                            final short respSerialNo = b.getRespSerialNo();
                            final MessageRecordService.RespMessageRecordEvent event = new MessageRecordService.RespMessageRecordEvent(m);
                            messageRecordService.responseMessage(event);
//                            messageRecordService.crudAndConsumer(repo -> {
//                                repo.findOne((Specification<MessageRecord>) (root, query, criteriaBuilder) -> {
//                                    final LocalDateTime now = LocalDateTime.now();
//
//                                    List<Predicate> predicates = new ArrayList<>(5);
//                                    predicates.add(criteriaBuilder.equal(root.get("simNo"), h.getSimNo()));
//                                    predicates.add(criteriaBuilder.equal(root.get("serialNo"), b.getRespSerialNo()));
//                                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), now));
//                                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("status"), MessageRecordStatus.NOT_RESPONDING));
//                                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), now.minusMinutes(20)));
//                                    return query
//                                            .where(predicates.toArray(new Predicate[0])).getRestriction();
//                                }).ifPresent(messageRecord -> {
//                                    final MessageRecord response = MessageRecord
//                                            .builder()
//                                            .flowTo(MessageFlow.RECEIVE)
//                                            .serialNo((int) h.getSerialNo())
//                                            .simNo(h.getSimNo())
//                                            .messageType((int) h.getId())
//                                            .status(MessageRecordStatus.RESPONDED)
//                                            .detail(MessageEncoder.writeAsString(m))
//                                            .build();
//                                    repo.save(response);
//                                    messageRecord.setStatus(MessageRecordStatus.RESPONDED);
//                                    messageRecord.setResponse(response);
//                                    repo.save(messageRecord);
//                                });
//                            });
//                            reactiveRedisTemplate
//                                    .delete(h.getSimNo() + "::" + respSerialNo)
//                                    .subscribe();
                        }));
    }
}
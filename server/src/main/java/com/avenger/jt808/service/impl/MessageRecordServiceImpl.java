package com.avenger.jt808.service.impl;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.Header;
import com.avenger.jt808.domain.Message;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.domain.entity.MessageRecord;
import com.avenger.jt808.domain.repository.MessageRecordRepository;
import com.avenger.jt808.enums.MessageFlow;
import com.avenger.jt808.enums.MessageRecordStatus;
import com.avenger.jt808.server.MessageEncoder;
import com.avenger.jt808.service.MessageRecordService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import reactor.core.publisher.Flux;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
        final Specification<MessageRecord> params = (root, query, criteriaBuilder) -> {
            final LocalDateTime now = LocalDateTime.now();

            List<Predicate> predicates = new ArrayList<>(4);
            predicates.add(criteriaBuilder.equal(root.get("simNo"), simNo));
            predicates.add(criteriaBuilder.equal(root.get("serialNo"), serialNo));
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), now));
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("flowTo"), MessageFlow.SEND));
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), now.minusMinutes(20)));
            return query
                    .where(predicates.toArray(new Predicate[0])).getRestriction();
        };

        return Flux.create(fluxSink -> {
            for (int i = 0; i < 500; i++) {
                final Optional<MessageRecord> one = messageRecordRepository.findOne(params);
                if (one.isPresent()) {
                    final MessageRecord messageRecord = one.get();
                    log.info("message id = {}, status = {}", messageRecord.getId(), messageRecord.getStatus().name());
                    if (messageRecord.getStatus() == MessageRecordStatus.RESPONDED) {
                        fluxSink.next(Collections.singletonMap("message", messageRecord.getResponse()));
                        fluxSink.complete();
                        return;
                    } else {
                        fluxSink.next(Collections.singletonMap("message", "已发送，等待终端回复"));
                    }
                } else {
                    fluxSink.next(Collections.singletonMap("message", "发送中"));
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    log.error("InterruptedException", e);
                }
            }

            fluxSink.error(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "发送失败"));
        });
    }

    @Override
    @EventListener
    @Transactional
    public void responseMessage(RespMessageRecordEvent respMessageRecordEvent) {
        final Message source = (Message) respMessageRecordEvent.getSource();
        final Header h = source.getHeader();
        final Body b = source.getMsgBody();

        messageRecordRepository.findOne((Specification<MessageRecord>) (root, query, criteriaBuilder) -> {
            final LocalDateTime now = LocalDateTime.now();

            List<Predicate> predicates = new ArrayList<>(5);
            predicates.add(criteriaBuilder.equal(root.get("simNo"), h.getSimNo()));
            predicates.add(criteriaBuilder.equal(root.get("serialNo"), b.getRespSerialNo()));
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), now));
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("status"), MessageRecordStatus.NOT_RESPONDING));
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), now.minusMinutes(20)));
            return query
                    .where(predicates.toArray(new Predicate[0])).getRestriction();
        }).ifPresent(messageRecord -> {
            final MessageRecord response = MessageRecord
                    .builder()
                    .flowTo(MessageFlow.RECEIVE)
                    .serialNo((int) h.getSerialNo())
                    .simNo(h.getSimNo())
                    .messageType((int) h.getId())
                    .status(MessageRecordStatus.RESPONDED)
                    .detail(MessageEncoder.writeAsString(source))
                    .build();
            messageRecordRepository.save(response);
            messageRecord.setStatus(MessageRecordStatus.RESPONDED);
            messageRecord.setResponse(response);
            messageRecordRepository.save(messageRecord);
        });
    }

    @Override
    @EventListener
    @Transactional
    public void newMessage(NewMessageRecordEvent respMessageRecordEvent) {
        final Message source = (Message) respMessageRecordEvent.getSource();
        final Header h = source.getHeader();
        final WritingMessageType annotation = source.getMsgBody().getClass().getAnnotation(WritingMessageType.class);
        final MessageRecord messageRecord = MessageRecord
                .builder()
                .flowTo(MessageFlow.SEND)
                .serialNo((int) h.getSerialNo())
                .simNo(h.getSimNo())
                .messageType((int) h.getId())
                .status(MessageRecordStatus.NEW)
                .detail(MessageEncoder.writeAsString(source))
                .build();
        Optional.ofNullable(annotation).ifPresent(a -> messageRecord.setMessageType((int) a.type()));
        messageRecordRepository.save(messageRecord);
    }


}

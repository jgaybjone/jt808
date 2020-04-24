package com.avenger.jt808.handler;

import com.avenger.jt808.UploadCompleteMsg;
import com.avenger.jt808.UploadCompleteRespMsg;
import com.avenger.jt808.domain.EncryptionForm;
import com.avenger.jt808.domain.Header;
import com.avenger.jt808.domain.Message;
import com.avenger.jt808.server.MessageHandler;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.Collections;

/**
 * Created by jg.wang on 2020/4/24.
 * Description:
 */
public class UploadFinishHandler implements MessageHandler {
    @Override
    public int getId() {
        return 0x1212;
    }

    @Override
    public Publisher<Message> process(Message message) {
        return Mono.just(message).filter(m -> m.getMsgBody() instanceof UploadCompleteMsg)
                .map(m -> {
                    final Header h = m.getHeader();
                    final UploadCompleteMsg b = (UploadCompleteMsg) m.getMsgBody();

                    final Header header = new Header(h.getSimNo(), false, EncryptionForm.NOTHING);
                    final UploadCompleteRespMsg uploadCompleteRespMsg = new UploadCompleteRespMsg();
                    uploadCompleteRespMsg.setFileName(b.getFileName());
                    uploadCompleteRespMsg.setFileType(b.getFileType());
                    uploadCompleteRespMsg.setFinished((byte) 0x00);
                    uploadCompleteRespMsg.setMissingPackets(Collections.emptyList());
                    final Message resp = new Message();
                    resp.setHeader(header);
                    resp.setMsgBody(uploadCompleteRespMsg);
                    return resp;
                });
    }
}

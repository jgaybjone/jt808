package com.avenger.jt808.util;

import com.avenger.jt808.base.pbody.CommonMsg;
import com.avenger.jt808.domain.EncryptionForm;
import com.avenger.jt808.domain.Header;
import com.avenger.jt808.domain.Message;
import lombok.experimental.UtilityClass;

/**
 * Created by jg.wang on 2020/4/24.
 * Description:
 */
@UtilityClass
public class CommonMessageUtils {

    public static Message success(Header h) {
        final Message message = new Message();
        final Header header = new Header(((byte) 0x8001), h.getSimNo(), false, EncryptionForm.NOTHING);
        message.setHeader(header);
        final CommonMsg commonMsg = new CommonMsg();
        commonMsg.setRespId(h.getId());
        commonMsg.setResult(CommonResult.SUCCESS);
        commonMsg.setRespSerialNo(h.getSerialNo());
        message.setMsgBody(commonMsg);
        return message;
    }
}

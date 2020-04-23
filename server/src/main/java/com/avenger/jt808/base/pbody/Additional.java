package com.avenger.jt808.base.pbody;

import com.avenger.jt808.base.annotation.AdditionalAble;
import com.avenger.jt808.domain.ByteSerialize;

/**
 * Created by jg.wang on 2020/4/10.
 * Description:
 */
public interface Additional extends ByteSerialize {
    default void setId(byte b) {
    }

    default byte getId() {
        return this.getClass().getAnnotation(AdditionalAble.class).type();
    }
}

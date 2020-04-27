package com.avenger.jt808.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerationException;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * Created by jg.wang on 2019/10/26.
 * Description:
 */
@Slf4j
public class SnowflakeIdGen implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object o) throws HibernateException {
        try {
            final Object id = FieldUtils.readField(o, "id", true);
            if (id != null && Integer.parseInt(String.valueOf(id)) > 0) {
                return (Serializable) id;
            }
        } catch (IllegalAccessException e) {
            log.error("生产主键错误", e);
            throw new IdentifierGenerationException("生产主键错误", e);
        }
        return SnowflakesTools.getInstanceSnowflake().nextId();
    }
}

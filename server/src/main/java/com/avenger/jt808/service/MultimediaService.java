package com.avenger.jt808.service;

import com.avenger.jt808.domain.entity.GpsRecord;
import com.avenger.jt808.domain.entity.Multimedia;
import com.avenger.jt808.domain.repository.MultimediaRepository;

/**
 * Created by jg.wang on 2020/4/30.
 * Description:
 */
public interface MultimediaService extends BaseService<Multimedia, Long, MultimediaRepository> {

    void save(Multimedia multimedia, GpsRecord record);
}

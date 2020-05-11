package com.avenger.jt808.domain.repository;

import com.avenger.jt808.domain.entity.CircularArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by jg.wang on 2020/5/9.
 * Description:
 */
public interface CircularAreaRepository extends JpaRepository<CircularArea, Integer>, JpaSpecificationExecutor<CircularArea> {
}

package com.avenger.jt808.domain.repository;

import com.avenger.jt808.domain.entity.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jg.wang on 2020/4/27.
 * Description:
 */
@Repository
public interface TerminalRepository extends JpaRepository<Terminal, Long> {
}

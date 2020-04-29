package com.avenger.jt808.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by jg.wang on 2020/4/29.
 * Description:
 */
@Entity
@Table(name = "terminal_setting", schema = "public", catalog = "postgres")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TerminalSetting implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)

    private Long id;
    @Column(name = "setting_id", nullable = true)
    private Integer settingId;
    @Column(name = "value", nullable = true, length = 200)
    private String value;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    @Generated(GenerationTime.INSERT)
    private Timestamp createdAt;

    @Column(name = "sim_no", nullable = true, length = 20)
    private String simNo;

}

package com.avenger.jt808.domain.entity;

import com.avenger.jt808.enums.EventItem;
import com.avenger.jt808.enums.MediaFormat;
import com.avenger.jt808.enums.MultimediaEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by jg.wang on 2020/4/30.
 * Description:
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "multimedia_event", schema = "public", catalog = "postgres")
@DynamicInsert
public class MultimediaEvent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "sim_no", nullable = false, length = 20)
    private String simNo;
    @Column(name = "multimedia_id", nullable = false)
    private int multimediaId;
    @Column(name = "type", nullable = false)
    private MultimediaEventType type;
    @Column(name = "encoding_format", nullable = false)
    private MediaFormat encodingFormat;
    @Column(name = "event_type", nullable = false)
    private EventItem eventType;
    @Column(name = "channel", nullable = false)
    private Integer channel;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    @Generated(GenerationTime.INSERT)
    private Timestamp createdAt;

}

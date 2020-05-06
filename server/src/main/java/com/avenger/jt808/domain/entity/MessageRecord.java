package com.avenger.jt808.domain.entity;

import com.avenger.jt808.enums.MessageFlow;
import com.avenger.jt808.enums.MessageRecordStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by jg.wang on 2020/5/5.
 * Description:
 */
@Entity
@Table(name = "message_record", schema = "public", catalog = "postgres")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "message_type", nullable = false)
    private Integer messageType;
    @Column(name = "created_at", nullable = true)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "serial_no", nullable = false)
    private Integer serialNo;
    @Column(name = "detail", nullable = false, length = -1)
    private String detail;
    @Column(name = "sim_no", nullable = false, length = 20)
    private String simNo;
    @Column(name = "flow_to", nullable = true)
    @Enumerated(value = EnumType.ORDINAL)
    private MessageFlow flowTo;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "response_id", referencedColumnName = "id", nullable = true)
    private MessageRecord response;
    @Column(name = "status", nullable = false)
    private MessageRecordStatus status;
}

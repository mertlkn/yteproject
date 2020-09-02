package yteproject.application.entities;

import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
@EqualsAndHashCode(of="id")
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="idgen")
    public Long id;
    @Version
    @Column(name="VERSION")
    private Long version;
    @CreatedDate
    @Column(name="CREATION_DATE")
    private LocalDate creationDate;
    @LastModifiedDate
    @Column(name="LAST_MODIFIED_DATE")
    private LocalDate lastModifiedDate;

}

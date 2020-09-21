package yteproject.application.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@SequenceGenerator(name="idgen",sequenceName="EVENT_SEQ")
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"eventName"})})
public class Events extends AbstractEntity{
    private String eventName;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;
    @ManyToMany(mappedBy = "appliedEvents",cascade = CascadeType.ALL)
    private Set<People> participants = new HashSet<>();
    @OneToMany
    @JoinColumn(name = "event_name")
    private Set<Questions> questions = new HashSet<>();
    @OneToMany
    @JoinColumn(name = "event_name")
    private Set<Survey> surveys = new HashSet<>();
    private Long quota;
    private Double latitude;
    private Double longitude;
    @AssertTrue(message = "Start date can't be after end date")
    public boolean isEndAfterStart() {
        return eventEndTime.isAfter(eventStartTime);
    }

}

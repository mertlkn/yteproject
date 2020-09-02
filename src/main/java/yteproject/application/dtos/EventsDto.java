package yteproject.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.AssertTrue;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventsDto {
    private String eventName;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;
    private Long quota;
    private Double latitude;
    private Double longitude;
    @AssertTrue(message = "Start date can't be after end date")
    public boolean isEndAfterStart() {
        return eventEndTime.isAfter(eventStartTime);
    }

}

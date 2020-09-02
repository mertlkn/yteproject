package yteproject.application.dtos;

import lombok.*;
import yteproject.application.validators.TcKimlikNo;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PeopleDto {
    private String name;
    private String surname;
    @TcKimlikNo
    private String tcKimlikNo;

    public String toString(String eventName) {
        return "Name: "+this.name+", Surname: "+this.surname+", TC Kimlik No :"+this.tcKimlikNo+", Event Name: "+eventName;
    }
}

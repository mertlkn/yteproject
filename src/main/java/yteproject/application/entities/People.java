package yteproject.application.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yteproject.application.validators.TcKimlikNo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@SequenceGenerator(name="idgen",sequenceName="PEOPLE_SEQ")
public class People extends AbstractEntity {
    private String name;
    private String surname;
    private String tcKimlikNo;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "applications",
            joinColumns = @JoinColumn(name = "people_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<Events> appliedEvents = new HashSet<>();

    public People(String name,String surname,String tcKimlikNo,Set<Events> appliedEvents) {
        this.name=name;
        this.surname=surname;
        this.tcKimlikNo=tcKimlikNo;
        this.appliedEvents=appliedEvents;
    }

    public Long getId() {
        return super.id;
    }

}

package yteproject.application.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yteproject.application.validators.TcKimlikNo;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

@Entity
@Setter
@Getter
@NoArgsConstructor
@SequenceGenerator(name="idgen",sequenceName="QUESTION_SEQ")
public class Questions extends AbstractEntity{
    private String name;
    private String surname;
    private String tcKimlikNo;
    private String email;
    private String question;
}

package yteproject.application.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name="idgen",sequenceName="SURVEY_SEQ")
public class Survey extends AbstractEntity{
    private Double date;
    private Double content;
}

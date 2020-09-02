package yteproject.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionsDto {
    private String name;
    private String surname;
    private String tcKimlikNo;
    private String email;
    private String question;
}

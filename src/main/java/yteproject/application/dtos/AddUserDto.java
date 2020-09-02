package yteproject.application.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Set;

@Getter
@Setter
public class AddUserDto {
    @Column(unique = true)
    private String username;
    private String password;
    private Set<String> authorities;
}

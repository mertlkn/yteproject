package yteproject.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yteproject.application.dtos.AddUserDto;
import yteproject.application.entities.Authority;
import yteproject.application.entities.Users;
import yteproject.application.repositories.AuthorityRepository;
import yteproject.application.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;

    public String addUser(AddUserDto addUserDto) {
        Set<Authority> authorities = addUserDto
                .getAuthorities()
                .stream()
                .map(authority -> new Authority(null, new HashSet<>(), authority))
                .collect(Collectors.toSet());
        authorityRepository.saveAll(authorities);
        Users users = new Users(null,addUserDto.getUsername(),addUserDto.getPassword(),true,true,true,true,authorities);
        userRepository.save(users);
        return "Success";
    }
}

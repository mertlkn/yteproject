package yteproject.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yteproject.application.dtos.AddUserDto;
import yteproject.application.services.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/addUser")
    @PreAuthorize("permitAll()")
    public String addUser(@RequestBody AddUserDto addUserDto) {
        return userService.addUser(addUserDto);
    }
}

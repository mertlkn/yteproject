package yteproject.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yteproject.application.dtos.AddUserDto;
import yteproject.application.dtos.LoginRequest;
import yteproject.application.dtos.LoginResponse;
import yteproject.application.services.LoginService;
import yteproject.application.services.UserService;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return loginService.login(loginRequest);
    }

}

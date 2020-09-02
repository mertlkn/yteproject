package yteproject.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import yteproject.application.dtos.LoginRequest;
import yteproject.application.dtos.LoginResponse;
import yteproject.application.messages.MessageResponse;
import yteproject.application.messages.MessageType;
import yteproject.application.util.JwtUtil;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationProvider authenticationProvider;
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    public LoginResponse login(LoginRequest loginRequest) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword());
        try {
            Authentication authentication = authenticationProvider.authenticate(authenticationToken);
            String jwtToken = JwtUtil.generateToken(authentication, secretKey, 7);
            return new LoginResponse(jwtToken,new MessageResponse("Successfully logged in", MessageType.SUCCESS));
        }
        catch (AuthenticationException ex) {
            return new LoginResponse("",new MessageResponse("Username or password incorrect", MessageType.ERROR));
        }
    }
}

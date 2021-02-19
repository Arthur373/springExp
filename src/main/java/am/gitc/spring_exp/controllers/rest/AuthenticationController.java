package am.gitc.spring_exp.controllers.rest;

import am.gitc.spring_exp.entity.UserDto;
import am.gitc.spring_exp.entity.UserModel;
import am.gitc.spring_exp.security.JwtTokenProvider;
import am.gitc.spring_exp.services.UserServ;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserServ userServ;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationController(AuthenticationManager authenticationManager, UserServ userServ, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userServ = userServ;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody UserDto userDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
            UserModel userModel = userServ.getUserByEmail(userDto.getEmail());
            String token = jwtTokenProvider.generateToken(userModel.getEmail(), userModel.getRole().name());
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("email", userModel.getEmail());
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHolder = new SecurityContextLogoutHandler();
        securityContextLogoutHolder.logout(request,response,null);
    }
}

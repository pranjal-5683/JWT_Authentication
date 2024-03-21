package com.adminbackend.controller;

import com.adminbackend.config.UserAuthProvider;
import com.adminbackend.dto.CredentialsDto;
import com.adminbackend.dto.SignUpDto;
import com.adminbackend.dto.UserDto;
import com.adminbackend.service.EmailService;
import com.adminbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {


    private final UserService userService;

    private final UserAuthProvider userAuthProvider;

    private final EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto){
        UserDto userDto = userService.login(credentialsDto);

        userDto.setToken(userAuthProvider.createToken(userDto.getEmail()));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto){
        UserDto userDto = userService.register(signUpDto);

        userDto.setToken(userAuthProvider.createToken(userDto.getEmail()));

        return ResponseEntity.created(URI.create("/users/" + userDto.getId()))
                .body(userDto);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> requestMap) {
        String email = requestMap.get("email");
        UserDto userDto = userService.forgotPassword(email);
        userDto.setToken(userAuthProvider.createToken(userDto.getEmail()));
        String link = "http://localhost:3000/reset-password/" + userDto.getId() + "/" + userDto.getToken();
        emailService.sendEmail(userDto.getEmail(), "Reset Password",
                "<h1>Please Click on below link to reset your password</h1><p>" + link + "</p>");

        return ResponseEntity.ok(Map.of("status", "Mail Sent to your registered Email id"));
    }

    @PostMapping("/reset-password/{id}/{token}")
    public ResponseEntity<?> updatePassword(@PathVariable String id, @PathVariable String token,
                                            @RequestBody Map<String, String> requestMap) {
        String newPassword = requestMap.get("password");
        return handlePasswordReset(id, token, newPassword);
    }

    private ResponseEntity<?> handlePasswordReset(String id, String token, String newPassword) {

        UserDto userDto = userService.findUserById(Long.parseLong(id));
        if(userDto == null){
            return ResponseEntity.badRequest().body(Map.of("status", "User does not exist"));
        }

        userDto.setToken(token);
        Authentication authentication = userAuthProvider.validateToken(token);

        if(authentication.isAuthenticated()){
            UserDto updatedUser = userService.updateUserPassword(userDto.getEmail(),newPassword);
            updatedUser.setToken(token);
            return ResponseEntity.ok(Map.of("email", updatedUser.getEmail(), "status", "verified"));
        } else {
            return ResponseEntity.ok(Map.of("email", userDto.getEmail(), "status", "Not verified"));
        }
    }
}

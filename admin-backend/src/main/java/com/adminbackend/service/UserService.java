package com.adminbackend.service;

import com.adminbackend.dto.CredentialsDto;
import com.adminbackend.dto.SignUpDto;
import com.adminbackend.dto.UserDto;
import com.adminbackend.entity.User;
import com.adminbackend.exception.AppException;
import com.adminbackend.mapper.UserMapper;
import com.adminbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @SneakyThrows
    public UserDto findByEmail(String email){
       User user =  userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

       return userMapper.mapToUserDto(user);
    }

    @SneakyThrows
    public UserDto login(CredentialsDto credentialsDto){
        User user =  userRepository.findByEmail(credentialsDto.getEmail())
                .orElseThrow(() -> new AppException("Unknown user with email: " + credentialsDto.getEmail(), HttpStatus.NOT_FOUND));


        if(passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())){
            return userMapper.mapToUserDto(user);
        }

        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    @SneakyThrows
    public UserDto register(SignUpDto signUpDto){
        Optional<User> optionalUser =  userRepository.findByEmail((signUpDto.getEmail()));

        if(optionalUser.isPresent()){
            throw new AppException("User with email " + signUpDto.getEmail() + " already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.mapToUser(signUpDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.getPassword())));

        User savedUser = userRepository.save((user));

        return userMapper.mapToUserDto(savedUser);
    }

    @SneakyThrows
    public UserDto forgotPassword(String email) {
        Optional<User> optionalUser =  userRepository.findByEmail(email);

        if(optionalUser.isEmpty()){
            throw new AppException("User with email " + email + " does not exists", HttpStatus.BAD_REQUEST);
        }
        User user = optionalUser.get();
        return userMapper.mapToUserDto(user);
    }

    @SneakyThrows
    public UserDto findUserById(long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()){
            throw new AppException("User with id " + id + " does not exists", HttpStatus.BAD_REQUEST);
        }

        return userMapper.mapToUserDto(optionalUser.get());
    }

    public UserDto updateUserPassword(String email, String newPassword) {
        User user =  userRepository.findByEmail(email).get();
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(newPassword)));
        User savedUser = userRepository.save((user));
        return userMapper.mapToUserDto(savedUser);
    }
}

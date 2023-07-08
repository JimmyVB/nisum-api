package com.nisum.service.impl;

import com.nisum.config.PasswordConfig;
import com.nisum.dto.user.UserDto;
import com.nisum.dto.user.UserMapper;
import com.nisum.dto.user.UserRequest;
import com.nisum.model.User;
import com.nisum.repository.UserRepository;
import com.nisum.service.UserService;
import com.nisum.utils.EmailValidator;
import com.nisum.utils.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private UserRepository repository;

    private final PasswordValidator passwordValidator;


    public UserServiceImpl(PasswordConfig config) {
        String passwordRegex = config.getPasswordRegex();
        passwordValidator = new PasswordValidator(passwordRegex);
    }

    @Override
    @Transactional
    public UserDto save(UserRequest request) {

        if (!EmailValidator.isValidEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email format");
        }

        if (!passwordValidator.isValidPassword(request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password format");
        }

        User user = repository.findUserByEmail(request.getEmail());

        if(Objects.nonNull(user)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        User newUser = mapper.toModel(request);
        User savedUser = repository.save(newUser);
        String token = UUID.randomUUID().toString();
        savedUser.setToken(token);
        return mapper.toDto(repository.save(savedUser));
    }

    @Override
    public UserDto update(UUID uuid, UserRequest request) {
        User user = findByUuidOrThrow(uuid);
        mapper.updateModel(request, user);
        return mapper.toDto(repository.save(user));
    }

    @Override
    public User findByUuidOrThrow(UUID uuid) {
        return repository.findById(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Not found User with id: " + uuid)
        );
    }

    @Override
    public boolean findEmail(UserRequest user) {
        return false;
    }
}
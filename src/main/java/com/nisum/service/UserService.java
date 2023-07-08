package com.nisum.service;

import com.nisum.dto.user.UserDto;
import com.nisum.dto.user.UserRequest;
import com.nisum.model.User;

import java.util.UUID;

public interface UserService {

    public UserDto save(UserRequest request);
    public UserDto update(UUID uuid, UserRequest request);
    public User findByUuidOrThrow(UUID uuid);
    public boolean findEmail(UserRequest request);
}

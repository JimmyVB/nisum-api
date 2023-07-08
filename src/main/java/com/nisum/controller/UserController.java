package com.nisum.controller;


import com.nisum.dto.user.UserDto;
import com.nisum.dto.user.UserRequest;
import com.nisum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody UserRequest request
    ) {
        try {
            UserDto result = userService.save(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (ResponseStatusException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", ex.getReason());
            return ResponseEntity.status(ex.getStatus()).body(errorResponse);
        }
    }

    @PatchMapping("/{userUuid}")
    public ResponseEntity<UserDto> update(
            @PathVariable UUID userUuid,
            @RequestBody UserRequest request
    ) {
        UserDto result = userService.update(userUuid, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}

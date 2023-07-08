package com.nisum.controller;

import com.nisum.dto.user.UserDto;
import com.nisum.dto.user.UserRequest;
import com.nisum.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testCreateUser_Success() {
        // Datos de prueba
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Juan Rodriguez");
        userRequest.setEmail("juan@rodriguez.org");
        userRequest.setPassword("P@ssw0rd");

        UserDto userDto = new UserDto();
        userDto.setId(UUID.randomUUID().toString());
        userDto.setName("Juan Rodriguez");
        userDto.setEmail("juan@rodriguez.org");

        Mockito.when(userService.save(Mockito.any(UserRequest.class))).thenReturn(userDto);

        ResponseEntity<?> responseEntity = userController.create(userRequest);

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(userDto, responseEntity.getBody());
    }

    @Test
    public void testCreateUser_DuplicateEmail_Error() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Juan Rodriguez");
        userRequest.setEmail("juan@rodriguez.org");
        userRequest.setPassword("P@ssw0rd");

        Mockito.when(userService.save(Mockito.any(UserRequest.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists"));

        ResponseEntity<?> responseEntity = userController.create(userRequest);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getBody() instanceof Map);

        Map<String, String> errorResponse = (Map<String, String>) responseEntity.getBody();
        Assertions.assertEquals("Email already exists", errorResponse.get("mensaje"));
    }

    @Test
    public void testUpdateUser_Success() {
        UUID userUuid = UUID.randomUUID();

        UserRequest userRequest = new UserRequest();
        userRequest.setName("Juan Rodriguez");
        userRequest.setEmail("juan@rodriguez.org");

        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setId(userUuid.toString());
        userRequest.setName("Juan Rodriguez");
        userRequest.setEmail("juan@rodriguez.org");

        Mockito.when(userService.update(Mockito.eq(userUuid), Mockito.any(UserRequest.class))).thenReturn(updatedUserDto);

        ResponseEntity<UserDto> responseEntity = userController.update(userUuid, userRequest);

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(updatedUserDto, responseEntity.getBody());
    }
}

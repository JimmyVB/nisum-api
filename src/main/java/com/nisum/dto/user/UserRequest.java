package com.nisum.dto.user;

import com.nisum.model.Phone;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRequest {

    private String name;
    private String email;
    private String password;
    private List<Phone> phones;
}

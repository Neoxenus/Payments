package com.my.model.entities;

import com.my.model.entities.enums.Role;
import lombok.Data;

@Data
public class User {
    private int id;
    private String username;
    private String phoneNumber;//unique
    private Role role;
    private String email;
    private String password;
}

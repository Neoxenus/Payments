package com.my.model.entities;

import com.my.model.entities.enums.Block;
import com.my.model.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class User {
    private int id;
    private String name;
    private String email;//unique
    private String phoneNumber;
    private Role role;

    private String password;
    private Block isBlocked;

    public User(String name, String phoneNumber, String email, String password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = Role.USER;
        this.email = email;
        this.password = password;
        this.isBlocked = Block.ACTIVE;
    }
}

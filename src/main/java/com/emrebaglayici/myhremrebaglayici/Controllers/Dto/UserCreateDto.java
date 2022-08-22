package com.emrebaglayici.myhremrebaglayici.Controllers.Dto;

import com.emrebaglayici.myhremrebaglayici.Entities.Abstracts.User;
import com.emrebaglayici.myhremrebaglayici.Entities.Concretes.Role;
import lombok.Setter;

@Setter
public class UserCreateDto {
    private String name;
    private Role role;

    public User toUser(){
        return User.builder()
                .name(this.name)
                .role(this.role)
                .build();
    }
}

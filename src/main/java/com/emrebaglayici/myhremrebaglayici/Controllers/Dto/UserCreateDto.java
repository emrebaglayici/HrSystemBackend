package com.emrebaglayici.myhremrebaglayici.Controllers.Dto;

import com.emrebaglayici.myhremrebaglayici.Entities.User;
import lombok.Setter;

@Setter
public class UserCreateDto {
    private String name;
    private String role;

    public User toUser(){
        return User.builder()
                .name(this.name)
                .role(this.role.substring(0,1).toUpperCase()+this.role.substring(1))
                .build();
    }
}

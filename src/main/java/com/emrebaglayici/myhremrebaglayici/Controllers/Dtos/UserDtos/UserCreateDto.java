package com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.UserDtos;

import com.emrebaglayici.myhremrebaglayici.Entities.User;
import lombok.Setter;

@Setter
public class UserCreateDto {
    private String name;
    private String role;

    public User toUser() {
        if (role.length()>0)
            this.role=role.substring(0, 1).toUpperCase() + this.role.substring(1);
        return User.builder()
                .name(this.name)
                .role(this.role)
                .build();
    }
}

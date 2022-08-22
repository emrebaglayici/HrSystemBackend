package com.emrebaglayici.myhremrebaglayici.Controllers.Dto;

import com.emrebaglayici.myhremrebaglayici.Entities.Concretes.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    private String name;
    private Role role;
}

package com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.UserDtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String role;
}

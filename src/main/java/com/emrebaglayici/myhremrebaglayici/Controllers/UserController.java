package com.emrebaglayici.myhremrebaglayici.Controllers;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IUser;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.UserDtos.UserCreateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.UserDtos.UserDto;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class UserController {
    private final IUser iUser;

    public UserController(IUser iUser) {
        this.iUser = iUser;
    }

    @PostMapping("user")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserCreateDto dto) {
        this.iUser.saveUser(dto);
    }

    @GetMapping("users")
    public Page<UserDto> listUsers(@PageableDefault(page = 0, size = 30) @SortDefault.SortDefaults
            ({@SortDefault(sort = "id", direction = Sort.Direction.ASC)}) Pageable pageable) {
        return iUser.listUsers(pageable)
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .role(user.getRole())
                        .build());

    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(this.iUser.deleteById(id));
    }

    @PatchMapping("/{id}/{name}")
    public UserDto updateUserName(@PathVariable Long id, @PathVariable String name) {
        User user= iUser.updateNameById(id,name);
        return UserDto.builder()
                .id(id)
                .name(name)
                .role(user.getRole())
                .build();
    }
}

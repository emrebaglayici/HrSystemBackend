package com.emrebaglayici.myhremrebaglayici.Controllers;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.UserService;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.UserCreateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.UserDto;
import com.emrebaglayici.myhremrebaglayici.Core.DataResult;
import com.emrebaglayici.myhremrebaglayici.Entities.Abstracts.User;
import com.emrebaglayici.myhremrebaglayici.Entities.Concretes.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping(value = "/add")
//    public ResponseEntity<?> add(@RequestBody User user){
//        return ResponseEntity.ok(this.userService.add(user));
//    }

    @PostMapping("addUser")
    public UserDto create(@RequestBody UserCreateDto dto){
        User user=this.userService.saveUser(dto.toUser());

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole().substring(0,1).toUpperCase()+user.getRole().substring(1))
                .build();
    }



//    @GetMapping("userWithRoles")
//    public DataResult<List<User>> listUserWithRoles(){
//        return userService.getAllUserByRoles();
//    }
//
//    @GetMapping("getAllUsers")
//    public DataResult<List<User>> listAllUsers(){
//        return userService.getAllUsers();
//    }
}

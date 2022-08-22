package com.emrebaglayici.myhremrebaglayici.Controllers;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.UserService;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.UserCreateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.UserDto;
import com.emrebaglayici.myhremrebaglayici.Core.*;
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
    public Result create(@RequestBody UserCreateDto dto) {
        DataResult<User> user = this.userService.saveUser(dto.toUser());
        if (user.getData() != null) {
            if (!user.getData().getName().equals("") || !user.getData().getRole().equals("")) {
                if (user.getData().getRole().equals("Candidates") || user.getData().getRole().equals("Hr")) {
                    System.out.println(user.getData());
                    return new SuccessDataResult<>(
                            UserDto.builder()
                                    .id(user.getData().getId())
                                    .name(user.getData().getName())
                                    .role(user.getData().getRole().substring(0, 1).toUpperCase() + user.getData().getRole().substring(1))
                                    .build()
                            , "Success");
                } else {
                    return new ErrorDataResult("Wrong role name!");
                }

            } else {
                return new ErrorDataResult("Fields are empty");
            }


        } else {

            return new ErrorResult("Error! Please check fields.");
        }

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

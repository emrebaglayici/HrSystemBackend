package com.emrebaglayici.myhremrebaglayici.Controllers;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.UserService;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.UserCreateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.UserDto;
import com.emrebaglayici.myhremrebaglayici.Core.*;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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

    @GetMapping("users")
    public Page<UserDto> listUsers(@PageableDefault(page = 0, size = 30) @SortDefault.SortDefaults({@SortDefault(sort = "id", direction = Sort.Direction.ASC)}) Pageable pageable) {

        return userService.listUsers(pageable)
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .role(user.getRole())
                        .build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(this.userService.deleteById(id));
    }

    @PutMapping("/updateUsersName")
    public Result updateUserName(@RequestParam Long id, @RequestParam String name) {
        return this.userService.updateNameById(id, name);
    }
}

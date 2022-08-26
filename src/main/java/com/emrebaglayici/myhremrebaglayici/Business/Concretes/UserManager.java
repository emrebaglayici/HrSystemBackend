package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.UserService;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.UserCreateDto;
import com.emrebaglayici.myhremrebaglayici.Core.*;
import com.emrebaglayici.myhremrebaglayici.Entities.Role;
import com.emrebaglayici.myhremrebaglayici.NotFountException;
import com.emrebaglayici.myhremrebaglayici.Repository.UserRepository;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserManager implements UserService {

    private final UserRepository userRepository;
    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(UserCreateDto dto) {
        if (!dto.toUser().getName().equals("") &&
                !dto.toUser().getName().equals("string") &&
                !dto.toUser().getRole().equals("") &&
                !dto.toUser().getName().equals("string")) {
            if (dto.toUser().getRole().equals(Role.CANDIDATES.getName()) ||
                    dto.toUser().getRole().equals(Role.HR.getName())) {
                this.userRepository.save(dto.toUser());
            } else {
                throw new NotFountException("Role is incorrect");
            }
        } else {
            throw new NotFountException("Please fill the gaps.");
        }
    }

    @Override
    public Result deleteById(Long id) {
        if (userRepository.existsUserById(id)) {
            this.userRepository.deleteById(id);
            return new SuccessDataResult<>("User deleted");
        } else
            return new ErrorDataResult<>("User not found");

    }

    @Override
    public Result updateNameById(Long id, String name) {
        User user;
        if (userRepository.existsUserById(id)) {
            user = this.userRepository.findById(id).get();
            user.setName(name);
            this.userRepository.save(user);
            return new SuccessResult("User's name changed");
        } else {
            return new ErrorResult("User not found");
        }
    }


    public Page<User> listUsers(Pageable page) {
        return userRepository.findAll(page);
    }

    //TODO ask how to check pageable sort parameter check if not "string"
}

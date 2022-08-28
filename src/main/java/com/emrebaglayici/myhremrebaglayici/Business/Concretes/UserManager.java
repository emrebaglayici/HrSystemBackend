package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.UserService;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.UserCreateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.UserDto;
import com.emrebaglayici.myhremrebaglayici.Core.*;
import com.emrebaglayici.myhremrebaglayici.Entities.Role;
import com.emrebaglayici.myhremrebaglayici.Exceptions.FillTheBlanks;
import com.emrebaglayici.myhremrebaglayici.Exceptions.NotFountException;
import com.emrebaglayici.myhremrebaglayici.Repository.UserRepository;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserManager implements UserService {

    private final UserRepository userRepository;
    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(UserCreateDto dto) {
        if (!dto.toUser().getName().equals("") && !dto.toUser().getRole().equals("")){
            if (dto.toUser().getRole().equals(Role.CANDIDATES.getName()) ||
                    dto.toUser().getRole().equals(Role.HR.getName())) {
                this.userRepository.save(dto.toUser());
            } else {
                throw new NotFountException("Role is not found");
            }
        } else {
            throw new FillTheBlanks("Please fill the gaps.");
        }
    }
    //TODO: find a way to check notnull

    @Override
    public User deleteById(Long id) {
        Optional<User> userOptional=userRepository.getUsersById(id);
        User user=userOptional.orElseThrow(()-> new NotFountException("User not found!"));
        this.userRepository.delete(user);
        return user;
    }

    @Override
    public User updateNameById(Long id, String name) {
        Optional<User> userOptional= userRepository.getUsersById(id);
        User user=userOptional.orElseThrow(()-> new NotFountException("User not found!"));
        user.setName(name);
        return this.userRepository.save(user);
    }


    public Page<User> listUsers(Pageable page) {
        return userRepository.findAll(page);
    }

    //TODO ask how to check pageable sort parameter check if not "string"
}

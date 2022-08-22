package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.UserService;
import com.emrebaglayici.myhremrebaglayici.Core.*;
import com.emrebaglayici.myhremrebaglayici.Repository.UserRepository;
import com.emrebaglayici.myhremrebaglayici.Entities.Abstracts.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManager implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        if (!this.userRepository.existsUserByName(user.getName())){
            this.userRepository.save(user);
        }else {

        }
//        if (!this.userRepository.existsUserById(user.getId())){
//            this.userRepository.save(user);
//        }
       return user;
    }


//    @Override
//    public Result add(User user) {
//        if (!this.userRepository.existsUserById(user.getId())){
//            this.userRepository.save(user);
//            return new SuccessDataResult<>(user,"User added");
//        }else
//            return new ErrorResult("The user already registered.");
//    }



//    @Override
//    public Result add(User user) {
//        this.userRepository.save(user);
//        return new SuccessDataResult<>("User Added");
//    }

//    @Override
//    public DataResult<List<User>> getAllUserByRoles() {
//        this.userRepository.getAllUserByRoles();
//        return new SuccessDataResult<>("Users and role are listed");
//    }
//
//    @Override
//    public DataResult<List<User>> getAllUsers() {
//
//        this.userRepository.getAllUsers();
//        return new SuccessDataResult<>("Users are listed.");
//    }

//    @Override
//    public Result add(User user) {
//        userRepository.save(user);
//        return new SuccessResult("User added");
//    }
}

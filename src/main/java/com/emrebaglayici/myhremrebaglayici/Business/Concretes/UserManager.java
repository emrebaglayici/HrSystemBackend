package com.emrebaglayici.myhremrebaglayici.Business.Concretes;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.UserService;
import com.emrebaglayici.myhremrebaglayici.Core.*;
import com.emrebaglayici.myhremrebaglayici.Repository.UserRepository;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserManager implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public DataResult saveUser(User user) {
        if (!this.userRepository.existsUserByName(user.getName())) {
            this.userRepository.save(user);
            return new SuccessDataResult<>(user, "User added successfully");
        } else {
            return new ErrorDataResult<>(user, "User already registered");
        }

    }

    @Override
    public Result deleteById(Long id) {
        if (userRepository.existsUserById(id)){
            this.userRepository.deleteById(id);
            return new SuccessDataResult<>("User deleted");
        }else
            return new ErrorDataResult<>("User not found");

    }

    @Override
    public Result updateNameById(Long id, String name) {
        User user;
        if (userRepository.existsUserById(id)){
            user= this.userRepository.findById(id).get();
            user.setName(name);
            this.userRepository.save(user);
            return new SuccessResult("User's name changed");
        }else{
            return new ErrorResult("User not found");
        }
    }


    public Page<User> listUsers(Pageable page){
            return userRepository.findAll(page);
    }

    //TODO ask how to check pageable sort parameter check if not "string"
}

package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IUserCheck;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import com.emrebaglayici.myhremrebaglayici.Repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserCheckManager implements IUserCheck {
    private final UserRepository userRepository;
    public UserCheckManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
}

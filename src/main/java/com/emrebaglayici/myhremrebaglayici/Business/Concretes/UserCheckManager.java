package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.UserCheckService;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import com.emrebaglayici.myhremrebaglayici.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCheckManager implements UserCheckService {
    private final UserRepository userRepository;

    @Autowired
    public UserCheckManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean checkHr(Long id) {
        User user=this.userRepository.getUsersById(id);
        return user.getRole().equals("Hr");
    }

    @Override
    public boolean checkCandidates(Long id) {
        User user=this.userRepository.getUsersById(id);
        return user.getRole().equals("Candidate");
    }

    @Override
    public boolean existsUser(Long id) {
        return this.userRepository.existsUserById(id);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }


}

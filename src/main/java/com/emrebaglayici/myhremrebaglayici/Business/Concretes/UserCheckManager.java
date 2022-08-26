package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.UserCheckService;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import com.emrebaglayici.myhremrebaglayici.Repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserCheckManager implements UserCheckService {
    private final UserRepository userRepository;
    public UserCheckManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean checkHr(Long id) {
        User user = this.userRepository.getUsersById(id);
        return user.getRole().equals("Hr");
    }

    @Override
    public boolean checkCandidates(Long id) {
        User user = this.userRepository.getUsersById(id);
        return user.getRole().equals("Candidates");
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

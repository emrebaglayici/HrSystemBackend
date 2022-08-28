package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.UserCheckService;
import com.emrebaglayici.myhremrebaglayici.Entities.Role;
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
        Optional<User> user=this.userRepository.findById(id);
        return user.map(value -> value.getRole().equals(Role.HR.getName())).orElse(false);
    }
    @Override
    public boolean checkCandidates(Long id) {
        Optional<User> user=this.userRepository.findById(id);
        return user.map(value -> value.getRole().equals(Role.CANDIDATES.getName())).orElse(false);
    }
    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }


}

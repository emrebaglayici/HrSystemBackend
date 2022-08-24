package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.UserCheckService;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import com.emrebaglayici.myhremrebaglayici.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
}

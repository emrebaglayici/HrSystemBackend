package com.emrebaglayici.myhremrebaglayici.Business.Abstracts;

import com.emrebaglayici.myhremrebaglayici.Entities.User;

import java.util.Optional;

public interface UserCheckService {
    boolean checkHr(Long id);
    boolean checkCandidates(Long id);

    boolean existsUser(Long id);

    Optional<User> getUserById(Long userId);
}

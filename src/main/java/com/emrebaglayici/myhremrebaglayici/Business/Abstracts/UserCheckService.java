package com.emrebaglayici.myhremrebaglayici.Business.Abstracts;

import com.emrebaglayici.myhremrebaglayici.Entities.User;

public interface UserCheckService {
    boolean checkHr(Long id);
    boolean checkCandidates(Long id);

    boolean existsUser(Long id);
}
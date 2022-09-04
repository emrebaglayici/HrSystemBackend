package com.emrebaglayici.myhremrebaglayici.Business.Abstracts;

import com.emrebaglayici.myhremrebaglayici.Entities.User;
import java.util.Optional;

public interface IUserCheck {
    Optional<User> getUserById(Long userId);
}

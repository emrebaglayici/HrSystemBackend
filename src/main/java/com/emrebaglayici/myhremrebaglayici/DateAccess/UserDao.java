package com.emrebaglayici.myhremrebaglayici.DateAccess;

import com.emrebaglayici.myhremrebaglayici.Entities.Abstracts.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Integer> {
}

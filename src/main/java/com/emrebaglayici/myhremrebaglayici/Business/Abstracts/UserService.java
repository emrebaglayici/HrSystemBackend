package com.emrebaglayici.myhremrebaglayici.Business.Abstracts;

import com.emrebaglayici.myhremrebaglayici.Core.DataResult;
import com.emrebaglayici.myhremrebaglayici.Core.Result;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserService {
    DataResult saveUser(User user);

    @Query("DELETE FROM User WHERE id = :id")
    Result deleteById(@Param("id") Long id);

    @Query("UPDATE User SET name = :name WHERE id = :id")
    Result updateNameById(@Param("id") Long id, @Param("name") String name);

    Page<User> listUsers(Pageable pageable);


}

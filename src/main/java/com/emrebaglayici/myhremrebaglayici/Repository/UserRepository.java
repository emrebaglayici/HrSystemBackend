package com.emrebaglayici.myhremrebaglayici.Repository;

import com.emrebaglayici.myhremrebaglayici.Core.DataResult;
import com.emrebaglayici.myhremrebaglayici.Entities.Abstracts.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("FROM User u JOIN Role r ON u.role.id=r.id")
    List<User> getAllUserByRoles();


    @Query("FROM User ")
    List<User> getAllUsers();
}

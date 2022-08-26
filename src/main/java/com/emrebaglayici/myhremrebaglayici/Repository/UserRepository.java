package com.emrebaglayici.myhremrebaglayici.Repository;

import com.emrebaglayici.myhremrebaglayici.Entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @Query("SELECT role FROM User where id=: user_id")
    String getRoleById(Long user_id);

    User getUsersById(Long user_id);

    boolean existsUserById(Long user_id);

    boolean existsUserByName(String name);

}

package com.emrebaglayici.myhremrebaglayici.Business.Abstracts;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.UserCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserService {
    void saveUser(UserCreateDto dto);
    @Query("DELETE FROM User WHERE id = :id")
    User deleteById(@Param("id") Long id);

    @Query("UPDATE User SET name = :name WHERE id = :id")
    User updateNameById(@Param("id") Long id, @Param("name") String name);
    Page<User> listUsers(Pageable pageable);


}

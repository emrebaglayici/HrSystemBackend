package com.emrebaglayici.myhremrebaglayici.Business.Abstracts;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.UserDtos.UserCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface IUser {
    void saveUser(UserCreateDto dto);
    User deleteById(Long id);
    User updateNameById(Long id,String name);
    Page<User> listUsers(Pageable pageable);

}

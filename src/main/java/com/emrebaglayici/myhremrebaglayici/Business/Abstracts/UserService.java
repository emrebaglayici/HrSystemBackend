package com.emrebaglayici.myhremrebaglayici.Business.Abstracts;

import com.emrebaglayici.myhremrebaglayici.Core.DataResult;
import com.emrebaglayici.myhremrebaglayici.Core.Result;
import com.emrebaglayici.myhremrebaglayici.Entities.Abstracts.User;
import org.springframework.data.jpa.repository.Query;

import javax.xml.crypto.Data;
import java.util.List;


public interface UserService {
//    Result add(User user);

//    DataResult<List<User>> getAllUserByRoles();
//
//    DataResult<List<User>> getAllUsers();

    /*Result add(User user);*/
    DataResult saveUser(User user);
}

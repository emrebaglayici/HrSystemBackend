package com.emrebaglayici.myhremrebaglayici.Repository;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
}

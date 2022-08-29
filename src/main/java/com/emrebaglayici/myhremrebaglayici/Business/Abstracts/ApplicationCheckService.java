package com.emrebaglayici.myhremrebaglayici.Business.Abstracts;

import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ApplicationCheckService {


    @Query("FROM Application WHERE id =:id")
    Optional<Application> findById(Long id);

}

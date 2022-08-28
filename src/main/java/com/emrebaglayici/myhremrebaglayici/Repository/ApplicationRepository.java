package com.emrebaglayici.myhremrebaglayici.Repository;

import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ApplicationRepository extends PagingAndSortingRepository<Application, Long> {

    @Query("SELECT userId FROM Application WHERE jobId =:jobId")
    Long getUserIdByJobId(Long jobId);
}

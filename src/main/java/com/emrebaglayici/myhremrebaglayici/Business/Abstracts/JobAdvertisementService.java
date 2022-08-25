package com.emrebaglayici.myhremrebaglayici.Business.Abstracts;

import com.emrebaglayici.myhremrebaglayici.Core.DataResult;
import com.emrebaglayici.myhremrebaglayici.Core.Result;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobAdvertisementService {
    DataResult addJobAds(JobAdvertisement jobAds);
    Page<JobAdvertisement> listJobAds(Pageable pageable);

    @Query("UPDATE JobAdvertisement SET salary = : salary WHERE id = :id")
    Result updateSalaryById(@Param("id")Long id,@Param("userId")Long userId,@Param("salary")double salary);

    @Query("DELETE FROM JobAdvertisement WHERE id =:id")
    Result deleteById(@Param("id") Long id,@Param("userId")Long userId);
}

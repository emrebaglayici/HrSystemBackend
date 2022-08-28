package com.emrebaglayici.myhremrebaglayici.Business.Abstracts;

import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.JobAdvertisementCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobAdvertisementService {
    void addJobAds(JobAdvertisementCreateDto jobAds);

    Page<JobAdvertisement> listJobAds(Pageable pageable);

    @Query("DELETE FROM JobAdvertisement WHERE id =:id")
    JobAdvertisement deleteById(@Param("id") Long id, @Param("userId") Long userId);

    @Query("UPDATE JobAdvertisement SET type = :type WHERE id = :id")
    JobAdvertisement updateTypeById(@Param("id") Long id, @Param("userId") Long userId, @Param("type") String type);

    @Query("UPDATE JobAdvertisement SET description = :description WHERE id = :id")
    JobAdvertisement updateDescriptionById(@Param("id") Long id, @Param("userId") Long userId, @Param("description") String description);

    @Query("UPDATE JobAdvertisement SET salary = :salary WHERE id = :id")
    JobAdvertisement updateSalaryById(@Param("id") Long id, @Param("userId") Long userId, @Param("salary") double salary);

    @Query("UPDATE JobAdvertisement SET active= :active WHERE id = :id")
    JobAdvertisement updateActive(@Param("id") Long id,@Param("userId") Long userId,@Param("active") boolean active);

}

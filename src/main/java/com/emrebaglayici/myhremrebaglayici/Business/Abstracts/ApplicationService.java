package com.emrebaglayici.myhremrebaglayici.Business.Abstracts;

import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.ApplicationCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplicationService {
    void applyJob(ApplicationCreateDto apply);

    Page<Application> listApply(Pageable pageable);

    @Query("UPDATE Application SET experienceYear =:experienceYear WHERE id =:id")
    Application updateExperienceYear(@Param("id") Long id, @Param("userId") Long userId, @Param("experienceYear") int experienceYear);

    @Query("UPDATE Application SET personalInfo =:personalInfo WHERE id =:id")
    Application updatePersonalInfo(@Param("id") Long id, @Param("userId") Long userId, @Param("personalInfo") String personalInfo);
}

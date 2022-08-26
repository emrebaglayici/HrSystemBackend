package com.emrebaglayici.myhremrebaglayici.Business.Abstracts;

import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.ApplyCreateDto;
import com.emrebaglayici.myhremrebaglayici.Core.Result;
import com.emrebaglayici.myhremrebaglayici.Entities.Apply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplyService {
    void applyJob(ApplyCreateDto apply);

    Page<Apply> listApply(Pageable pageable);

    @Query("UPDATE Apply SET experienceYear =:experienceYear WHERE id =:id")
    Result updateExperienceYear(@Param("id") Long id, @Param("userId") Long userId, @Param("experienceYear") int experienceYear);

    @Query("UPDATE Apply SET personalInfo =:personalInfo WHERE id =:id")
    Result updatePersonalInfo(@Param("id") Long id, @Param("userId") Long userId, @Param("experienceYear") String personalInfo);
}

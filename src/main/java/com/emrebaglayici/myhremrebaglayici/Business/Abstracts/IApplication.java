package com.emrebaglayici.myhremrebaglayici.Business.Abstracts;

import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.ApplicationDtos.ApplicationCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IApplication {
    Optional<Application> getApplicationById(Long id);
    void update(Long id,Long userId,Application application);
    void applyJob(ApplicationCreateDto apply);
    Page<Application> listApply(Pageable pageable);

}

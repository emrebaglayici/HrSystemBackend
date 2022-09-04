package com.emrebaglayici.myhremrebaglayici.Business.Abstracts;

import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.JobAdsDtos.JobAdvertisementCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IJobAdvertisement {
    Optional<JobAdvertisement> findById(Long id);

    void addJobAds(JobAdvertisementCreateDto jobAds);

    Page<JobAdvertisement> listJobAds(Pageable pageable);

    JobAdvertisement deleteById(Long id, Long userId);

    void update(Long id, Long userId, JobAdvertisement jobAdvertisement);
}

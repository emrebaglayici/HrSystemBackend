package com.emrebaglayici.myhremrebaglayici.Business.Abstracts;

import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;

import java.util.Optional;

public interface JobAdvertisementCheckService {
    boolean existsJob(Long id);
    boolean isActive(Long id);

    Optional<JobAdvertisement> getJobById(Long id);
}

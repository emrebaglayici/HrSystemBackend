package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.JobAdvertisementCheckService;
import com.emrebaglayici.myhremrebaglayici.Repository.JobAdvertisementRepository;
import org.springframework.stereotype.Service;

@Service
public class JobAdvertisementCheckManager implements JobAdvertisementCheckService {
    private final JobAdvertisementRepository jobAdvertisementRepository;

    public JobAdvertisementCheckManager(JobAdvertisementRepository jobAdvertisementRepository) {
        this.jobAdvertisementRepository = jobAdvertisementRepository;
    }

    @Override
    public boolean existsJob(Long id) {
        return this.jobAdvertisementRepository.existsById(id);
    }

    @Override
    public long count() {
        return jobAdvertisementRepository.count();
    }
}

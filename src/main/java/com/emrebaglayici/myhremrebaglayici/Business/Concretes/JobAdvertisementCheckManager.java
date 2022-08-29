package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.JobAdvertisementCheckService;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Repository.JobAdvertisementRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobAdvertisementCheckManager implements JobAdvertisementCheckService {
    private final JobAdvertisementRepository jobAdvertisementRepository;
    public JobAdvertisementCheckManager(JobAdvertisementRepository jobAdvertisementRepository) {
        this.jobAdvertisementRepository = jobAdvertisementRepository;
    }
    @Override
    public boolean existsJob(Long id) {
        Optional<JobAdvertisement> jobAds=this.jobAdvertisementRepository.findById(id);
        return jobAds.isPresent();
    }

    @Override
    public boolean isActive(Long id) {
        Optional<JobAdvertisement> jobAds=this.jobAdvertisementRepository.findById(id);
        return jobAds.map(JobAdvertisement::isActive).orElse(false);
    }

    @Override
    public Optional<JobAdvertisement> getJobById(Long id) {
        Optional<JobAdvertisement> jobAdvertisementOptional=this.jobAdvertisementRepository.findById(id);
        return jobAdvertisementOptional;
    }
}

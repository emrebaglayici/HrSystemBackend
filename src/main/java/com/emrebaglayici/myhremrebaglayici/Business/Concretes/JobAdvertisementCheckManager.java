package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IJobAdvertisementCheck;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Repository.JobAdvertisementRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobAdvertisementCheckManager implements IJobAdvertisementCheck {
    private final JobAdvertisementRepository jobAdvertisementRepository;
    public JobAdvertisementCheckManager(JobAdvertisementRepository jobAdvertisementRepository) {
        this.jobAdvertisementRepository = jobAdvertisementRepository;
    }
    @Override
    public boolean existsJob(Long id) {
        //Checks if job exists or not
        Optional<JobAdvertisement> jobAds=this.jobAdvertisementRepository.findById(id);
        return jobAds.isPresent();
    }

    @Override
    public boolean isActive(Long id) {
        //Checks job is active or not.
        Optional<JobAdvertisement> jobAds=this.jobAdvertisementRepository.findById(id);
        return jobAds.map(JobAdvertisement::isActive).orElse(false);
    }

    @Override
    public Optional<JobAdvertisement> getJobById(Long id) {
        //Find job advertisement by id.
        Optional<JobAdvertisement> jobAdvertisementOptional=this.jobAdvertisementRepository.findById(id);
        return jobAdvertisementOptional;
    }
}

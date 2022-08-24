package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.JobAdvertisementService;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.UserCheckService;
import com.emrebaglayici.myhremrebaglayici.Core.DataResult;
import com.emrebaglayici.myhremrebaglayici.Core.ErrorDataResult;
import com.emrebaglayici.myhremrebaglayici.Core.Result;
import com.emrebaglayici.myhremrebaglayici.Core.SuccessDataResult;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Repository.JobAdvertisementRepository;
import com.emrebaglayici.myhremrebaglayici.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobAdvertisementManager implements JobAdvertisementService {

    private final JobAdvertisementRepository jobAdvertisementRepository;
    private final UserCheckService userCheckService;

    @Autowired
    public JobAdvertisementManager(JobAdvertisementRepository jobAdvertisementRepository,UserCheckService userCheckService) {
        this.jobAdvertisementRepository = jobAdvertisementRepository;
        this.userCheckService=userCheckService;
    }

    @Override
    public DataResult addJobAds(Long id, JobAdvertisement jobAds) {
        if (this.userCheckService.checkHr(id)){
            this.jobAdvertisementRepository.save(jobAds);
            return new SuccessDataResult(jobAds,"Job Advertisement added successfully");
        }else
            return new ErrorDataResult("Candidates cannot add Job Advertisements.");
    }
}

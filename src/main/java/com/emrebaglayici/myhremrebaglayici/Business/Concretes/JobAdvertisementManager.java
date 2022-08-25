package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.JobAdvertisementService;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.UserCheckService;
import com.emrebaglayici.myhremrebaglayici.Core.*;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Repository.JobAdvertisementRepository;
import com.emrebaglayici.myhremrebaglayici.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public DataResult addJobAds(JobAdvertisement jobAds) {
        if (this.userCheckService.checkHr(jobAds.getUserId())){
            this.jobAdvertisementRepository.save(jobAds);
            return new SuccessDataResult(jobAds,"Job Advertisement added successfully");
        }else
            return new ErrorDataResult("Candidates cannot add Job Advertisements.");
    }

    @Override
    public Page<JobAdvertisement> listJobAds(Pageable pageable) {
        return this.jobAdvertisementRepository.findAll(pageable);
    }

    @Override
    public Result updateSalaryById(Long id, Long userId, double salary) {
        JobAdvertisement jobADs;
        if (this.userCheckService.checkHr(userId)){
            if (jobAdvertisementRepository.existsById(id)){
                jobADs=this.jobAdvertisementRepository.findById(id).get();
                jobADs.setSalary(salary);
                this.jobAdvertisementRepository.save(jobADs);
                return new SuccessDataResult<>("Job Advertisement's salary has changed.");
            }else
                return new ErrorResult("Job Advertisement not found.");
        }else{
            return new ErrorResult("Candidates cannot add job ads.");
        }
    }

    @Override
    public Result deleteById(Long id,Long userId) {
        if (this.userCheckService.checkHr(userId)){
            if (this.jobAdvertisementRepository.existsById(id)){
                this.jobAdvertisementRepository.deleteById(id);
                return new SuccessDataResult<>("Job Ads deleted");
            }else{
                return new ErrorResult("Job Ad not found");
            }
        }else{
            return new ErrorResult("Candidates cannot delete job ads.");
        }

    }

}

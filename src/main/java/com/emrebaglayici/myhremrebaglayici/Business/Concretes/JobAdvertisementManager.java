package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.ApplicationCheckService;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.JobAdvertisementService;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.UserCheckService;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.JobAdvertisementCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Exceptions.FillTheBlanksException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.NotFountException;
import com.emrebaglayici.myhremrebaglayici.Repository.JobAdvertisementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class JobAdvertisementManager implements JobAdvertisementService {

    private final JobAdvertisementRepository jobAdvertisementRepository;
    private final UserCheckService userCheckService;

    public JobAdvertisementManager(JobAdvertisementRepository jobAdvertisementRepository,
                                   UserCheckService userCheckService, ApplicationCheckService applicationCheckService) {
        this.jobAdvertisementRepository = jobAdvertisementRepository;
        this.userCheckService = userCheckService;
    }

    @Override
    public void addJobAds(JobAdvertisementCreateDto dto) {
        if (dto.toJobAds().getInterviewCount()>5 || dto.toJobAds().getInterviewCount()==0){
            throw new NotFountException("Interview count must be 1 to 5!");
        }
        if (dto.toJobAds().getUserId() != 0 && !dto.toJobAds().getType().equals("") &&
                dto.toJobAds().getSalary() != 0 && !dto.toJobAds().getDescription().equals("")) {
            if (userCheckService.checkHr(dto.toJobAds().getUserId())) {
                this.jobAdvertisementRepository.save(dto.toJobAds());
            } else
                throw new NotFountException("User must be exists and  Hr!");
        } else
            throw new FillTheBlanksException("Please fill in the blanks.");
    }
    //TODO: learn how to check nulls.

    @Override
    public Page<JobAdvertisement> listJobAds(Pageable pageable) {
        return this.jobAdvertisementRepository.findAll(pageable);
    }

    @Override
    public JobAdvertisement updateSalaryById(Long id, Long userId, double salary) {
        Optional<JobAdvertisement> jobAdsOptional = this.jobAdvertisementRepository.findById(id);
        JobAdvertisement jobAds = jobAdsOptional.orElseThrow(() -> new NotFountException("Job advertisement not found!"));
        if (!userCheckService.checkHr(userId)) {
            throw new NotFountException("User must be exists and Hr!");
        }
        jobAds.setSalary(salary);
        this.jobAdvertisementRepository.save(jobAds);
        return jobAds;
    }

    @Override
    public JobAdvertisement updateActive(Long id, Long userId, boolean active) {
        Optional<JobAdvertisement> jobAdsOptional=this.jobAdvertisementRepository.findById(id);
        JobAdvertisement jobAds=jobAdsOptional.orElseThrow(()->new NotFountException("Job advertisement not found!"));
        if (!this.userCheckService.checkHr(userId))
            throw new NotFountException("User must be exists and Hr!");
        jobAds.setActive(active);
        this.jobAdvertisementRepository.save(jobAds);
        return jobAds;
    }

    @Override
    public JobAdvertisement updateInterviewCount(Long id, Long userId, int interviewCount) {
        Optional<JobAdvertisement> jobAdsOptional=this.jobAdvertisementRepository.findById(id);
        JobAdvertisement jobAds=jobAdsOptional.orElseThrow(()->new NotFountException("Job advertisement not found"));
        if (!this.userCheckService.checkHr(userId))
            throw new NotFountException("User must be exists and Hr!");
        if (interviewCount>5 || interviewCount==0)
            throw new NotFountException("Interview count must be 1 to 5");
        jobAds.setInterviewCount(interviewCount);
        this.jobAdvertisementRepository.save(jobAds);
        return jobAds;
    }

    @Override
    public JobAdvertisement updateTypeById(Long id, Long userId, String type) {
        Optional<JobAdvertisement> jobAdsOptional = this.jobAdvertisementRepository.findById(id);
        JobAdvertisement jobADs = jobAdsOptional.orElseThrow(() -> new NotFountException("Job advertisement not found!"));
        if (!userCheckService.checkHr(userId))
            throw new NotFountException("User must be exists and Hr!");
        jobADs.setType(type);
        this.jobAdvertisementRepository.save(jobADs);
        return jobADs;
    }

    @Override
    public JobAdvertisement updateDescriptionById(Long id, Long userId, String description) {
        Optional<JobAdvertisement> jobAdsOptional = this.jobAdvertisementRepository.findById(id);
        JobAdvertisement jobAds = jobAdsOptional.orElseThrow(() -> new NotFountException("Job Ads Not Found!"));
        if (!this.userCheckService.checkHr(userId))
            throw new NotFountException("User must be exists and Hr!");
        jobAds.setDescription(description);
        this.jobAdvertisementRepository.save(jobAds);
        return jobAds;
    }

    @Override
    public JobAdvertisement deleteById(Long id, Long userId) {
        Optional<JobAdvertisement> jobAdsOptional = this.jobAdvertisementRepository.findById(id);
        JobAdvertisement jobAds = jobAdsOptional.orElseThrow(() -> new NotFountException("Job Ads not Found!"));
        if (!this.userCheckService.checkHr(userId))
            throw new NotFountException("User must be exists and Hr!");
        this.jobAdvertisementRepository.delete(jobAds);
        return jobAds;

    }

}

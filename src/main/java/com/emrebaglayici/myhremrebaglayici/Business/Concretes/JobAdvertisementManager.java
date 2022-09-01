package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.JobAdvertisementService;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.UserCheckService;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.JobAdvertisementCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Exceptions.FillTheBlanksException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.NotFountException;
import com.emrebaglayici.myhremrebaglayici.Helper.Helper;
import com.emrebaglayici.myhremrebaglayici.Repository.JobAdvertisementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class JobAdvertisementManager implements JobAdvertisementService {

    private final JobAdvertisementRepository jobAdvertisementRepository;
    private final UserCheckService userCheckService;

    public JobAdvertisementManager(JobAdvertisementRepository jobAdvertisementRepository,
                                   UserCheckService userCheckService) {
        this.jobAdvertisementRepository = jobAdvertisementRepository;
        this.userCheckService = userCheckService;
    }

    @Override
    public void addJobAds(JobAdvertisementCreateDto dto) {
        if (dto.toJobAds().getInterviewCount() > 5 || dto.toJobAds().getInterviewCount() == 0) {
            throw new NotFountException(Helper.INTERVIEW_COUNT_MUST_BE_1TO5);
        }
        if (dto.toJobAds().getUserId() != 0 && !dto.toJobAds().getType().equals("") &&
                dto.toJobAds().getSalary() != 0 && !dto.toJobAds().getDescription().equals("")) {
            if (userCheckService.checkHr(dto.toJobAds().getUserId())) {
                log.info("Job advertisement added successfully : " + dto.toJobAds());
                this.jobAdvertisementRepository.save(dto.toJobAds());
            } else
                throw new NotFountException(Helper.USER_MUST_BE_EXISTS_AND_HR);
        } else
            throw new FillTheBlanksException(Helper.FILL_ALL_BLANKS);
    }

    @Override
    public Page<JobAdvertisement> listJobAds(Pageable pageable) {
        return this.jobAdvertisementRepository.findAll(pageable);
    }

    @Override
    public JobAdvertisement updateSalaryById(Long id, Long userId, double salary) {
        Optional<JobAdvertisement> jobAdsOptional = this.jobAdvertisementRepository.findById(id);
        JobAdvertisement jobAds = jobAdsOptional.orElseThrow(() -> new NotFountException(Helper.JOB_ADVERTISEMENT_NOT_FOUND));
        if (!userCheckService.checkHr(userId)) {
            throw new NotFountException(Helper.USER_MUST_BE_HR);
        }
        jobAds.setSalary(salary);
        log.info("Job advertisement salary updated successfully : " + jobAds.getSalary());
        this.jobAdvertisementRepository.save(jobAds);
        return jobAds;
    }

    @Override
    public JobAdvertisement updateActive(Long id, Long userId, boolean active) {
        Optional<JobAdvertisement> jobAdsOptional = this.jobAdvertisementRepository.findById(id);
        JobAdvertisement jobAds = jobAdsOptional.orElseThrow(() -> new NotFountException(Helper.JOB_ADVERTISEMENT_NOT_FOUND));
        if (!this.userCheckService.checkHr(userId))
            throw new NotFountException(Helper.USER_MUST_BE_EXISTS_AND_HR);
        jobAds.setActive(active);
        log.info("Job advertisement active situation updated successfully : " + jobAds.isActive());
        this.jobAdvertisementRepository.save(jobAds);
        return jobAds;
    }

    @Override
    public JobAdvertisement updateInterviewCount(Long id, Long userId, int interviewCount) {
        Optional<JobAdvertisement> jobAdsOptional = this.jobAdvertisementRepository.findById(id);
        JobAdvertisement jobAds = jobAdsOptional.orElseThrow(() -> new NotFountException(Helper.JOB_ADVERTISEMENT_NOT_FOUND));
        if (!this.userCheckService.checkHr(userId))
            throw new NotFountException(Helper.USER_MUST_BE_EXISTS_AND_HR);
        if (interviewCount > 5 || interviewCount == 0)
            throw new NotFountException(Helper.INTERVIEW_COUNT_MUST_BE_1TO5);
        jobAds.setInterviewCount(interviewCount);
        log.info("Job advertisement interview count updated successfully : " + jobAds.getInterviewCount());
        this.jobAdvertisementRepository.save(jobAds);
        return jobAds;
    }

    @Override
    public JobAdvertisement updateTypeById(Long id, Long userId, String type) {
        Optional<JobAdvertisement> jobAdsOptional = this.jobAdvertisementRepository.findById(id);
        JobAdvertisement jobAds = jobAdsOptional.orElseThrow(() -> new NotFountException(Helper.JOB_ADVERTISEMENT_NOT_FOUND));
        if (!userCheckService.checkHr(userId))
            throw new NotFountException(Helper.USER_MUST_BE_EXISTS_AND_HR);
        jobAds.setType(type);
        log.info("Job advertisement type updated successfully : " + jobAds.getType());
        this.jobAdvertisementRepository.save(jobAds);
        return jobAds;
    }

    @Override
    public JobAdvertisement updateDescriptionById(Long id, Long userId, String description) {
        Optional<JobAdvertisement> jobAdsOptional = this.jobAdvertisementRepository.findById(id);
        JobAdvertisement jobAds = jobAdsOptional.orElseThrow(() -> new NotFountException(Helper.JOB_ADVERTISEMENT_NOT_FOUND));
        if (!this.userCheckService.checkHr(userId))
            throw new NotFountException(Helper.USER_MUST_BE_EXISTS_AND_HR);
        jobAds.setDescription(description);
        log.info("Job advertisement description updated successfully : " + jobAds.getDescription());
        this.jobAdvertisementRepository.save(jobAds);
        return jobAds;
    }

    @Override
    public JobAdvertisement deleteById(Long id, Long userId) {
        Optional<JobAdvertisement> jobAdsOptional = this.jobAdvertisementRepository.findById(id);
        JobAdvertisement jobAds = jobAdsOptional.orElseThrow(() -> new NotFountException(Helper.JOB_ADVERTISEMENT_NOT_FOUND));
        if (!this.userCheckService.checkHr(userId))
            throw new NotFountException(Helper.USER_MUST_BE_EXISTS_AND_HR);
        log.info("Job advertisement deleted successfully : " + jobAds);
        this.jobAdvertisementRepository.delete(jobAds);
        return jobAds;
    }
}

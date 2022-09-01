package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.ApplicationService;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.JobAdvertisementCheckService;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.UserCheckService;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.ApplicationCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import com.emrebaglayici.myhremrebaglayici.Exceptions.AlreadyCreatedException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.NotFountException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.PermissionException;
import com.emrebaglayici.myhremrebaglayici.Helper.Helper;
import com.emrebaglayici.myhremrebaglayici.Repository.ApplicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ApplicationManager implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserCheckService userCheckService;
    private final JobAdvertisementCheckService jobAdvertisementCheckService;

    public ApplicationManager(ApplicationRepository applicationRepository,
                              UserCheckService userCheckService,
                              JobAdvertisementCheckService jobAdvertisementCheckService) {
        this.applicationRepository = applicationRepository;
        this.userCheckService = userCheckService;
        this.jobAdvertisementCheckService = jobAdvertisementCheckService;
    }

    @Override
    public void applyJob(ApplicationCreateDto dto) {
        if (!jobAdvertisementCheckService.existsJob(dto.toApply().getJobId())) {
            throw new NotFountException(Helper.JOB_ADVERTISEMENT_NOT_FOUND);
        }
        if (!this.jobAdvertisementCheckService.isActive(dto.toApply().getJobId())) {
            throw new NotFountException(Helper.JOB_AD_NOT_ACTIVE);
        }
        Optional<User> applyUser = userCheckService.getUserById(dto.toApply().getUserId());
        User user = applyUser.orElseThrow(() -> new NotFountException("User with id : " + dto.toApply().getUserId() + " is not found"));
        if (userCheckService.checkHr(dto.toApply().getUserId())) {
            throw new PermissionException(Helper.HR_CANNOT_APPLY_JOB);
        }
        if (Objects.equals(this.applicationRepository.getUserIdByJobId(dto.toApply().getJobId()), dto.toApply().getUserId())) {
            throw new AlreadyCreatedException(Helper.USER_ALREADY_APPLIED);
        }
        log.info("Application save successfully : " + dto.toApply());
        this.applicationRepository.save(dto.toApply());
    }

    @Override
    public Page<Application> listApply(Pageable pageable) {
        return this.applicationRepository.findAll(pageable);
    }

    @Override
    public Application updateExperienceYear(Long id, Long userId, int experienceYear) {
        Optional<Application> applyOptional = this.applicationRepository.findById(id);
        Application apply = applyOptional.orElseThrow(() -> new NotFountException(Helper.APPLICATION_NOT_FOUND));
        Optional<User> userOptional = this.userCheckService.getUserById(userId);
        User user = userOptional.orElseThrow(() -> new NotFountException(Helper.USER_NOT_FOUND));
        if (!this.userCheckService.checkCandidates(userId))
            throw new PermissionException(Helper.USER_MUST_BE_CANDIDATES);
        if (!Objects.equals(apply.getUserId(), userId)) {
            throw new NotFountException(Helper.USER_NOT_FOUND);
        }
        apply.setExperienceYear(experienceYear);
        log.info("Application experience year updated successfully : " + apply.getExperienceYear());
        this.applicationRepository.save(apply);
        return apply;
    }

    @Override
    public Application updatePersonalInfo(Long id, Long userId, String personalInfo) {
        Optional<Application> applyOptional = this.applicationRepository.findById(id);
        Application apply = applyOptional.orElseThrow(() -> new NotFountException(Helper.APPLICATION_NOT_FOUND));
        Optional<User> userOptional = this.userCheckService.getUserById(userId);
        User user = userOptional.orElseThrow(() -> new NotFountException(Helper.USER_NOT_FOUND));
        if (!this.userCheckService.checkCandidates(userId))
            throw new PermissionException(Helper.USER_MUST_BE_CANDIDATES);
        if (!Objects.equals(apply.getUserId(), userId)) {
            throw new NotFountException(Helper.USER_NOT_FOUND);
        }
        apply.setPersonalInfo(personalInfo);
        log.info("Application personal info updated successfully : " + apply.getPersonalInfo());
        this.applicationRepository.save(apply);
        return apply;
    }
}

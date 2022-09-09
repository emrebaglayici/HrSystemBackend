package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IApplication;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IJobAdvertisementCheck;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IUserCheck;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.ApplicationDtos.ApplicationCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import com.emrebaglayici.myhremrebaglayici.Entities.Role;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import com.emrebaglayici.myhremrebaglayici.Exceptions.AlreadyCreatedException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.NotFoundException;
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
public class ApplicationManager implements IApplication {

    private final ApplicationRepository applicationRepository;
    private final IUserCheck iUserCheck;
    private final IJobAdvertisementCheck iJobAdvertisementCheck;

    public ApplicationManager(ApplicationRepository applicationRepository,
                              IUserCheck iUserCheck,
                              IJobAdvertisementCheck iJobAdvertisementCheck) {
        this.applicationRepository = applicationRepository;
        this.iUserCheck = iUserCheck;
        this.iJobAdvertisementCheck = iJobAdvertisementCheck;
    }

    @Override
    public void applyJob(ApplicationCreateDto dto) {
        if (!iJobAdvertisementCheck.existsJob(dto.toApply().getJobId())) {
            log.info("Job advertisement with id : " + dto.toApply().getJobId() + " is not found.");
            throw new NotFoundException(Helper.JOB_ADVERTISEMENT_NOT_FOUND);
        }
        if (!this.iJobAdvertisementCheck.isActive(dto.toApply().getJobId())) {
            log.info("Job Advertisement with id : " + dto.toApply().getJobId() + " is not active anymore");
            throw new NotFoundException(Helper.JOB_AD_NOT_ACTIVE);
        }
        Optional<User> applyUser = iUserCheck.getUserById(dto.toApply().getUserId());
        User user = applyUser.orElseThrow(() -> new NotFoundException("User with id : " + dto.toApply().getUserId() + " is not found"));
        if (!applyUser.get().getRole().equals(Role.CANDIDATES.getName())) {
            log.info("User with id : " + user.getId() + " is not candidates");
            throw new PermissionException(Helper.HR_CANNOT_APPLY_JOB);
        }
        if (Objects.equals(this.applicationRepository.getUserIdByJobId(dto.toApply().getJobId()), dto.toApply().getUserId())) {
            log.info("User already applied this application");
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
    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }

    @Override
    public void update(Long id, Long userId, Application application) {
        Optional<User> userOptional = this.iUserCheck.getUserById(userId);
        User user = userOptional.orElseThrow(() -> new NotFoundException(Helper.USER_NOT_FOUND));
        if (!userOptional.get().getRole().equals(Role.CANDIDATES.getName())) {
            log.info("User with id : " + user.getId() + " is not candidates");
            throw new PermissionException(Helper.USER_MUST_BE_CANDIDATES);
        }
        if (!Objects.equals(application.getUserId(), userId)) {
            log.info("Application not found with id : " + application.getUserId());
            throw new NotFoundException(Helper.USER_NOT_FOUND);
        }
        log.info("Application updated successfully");
        this.applicationRepository.save(application);
    }
}

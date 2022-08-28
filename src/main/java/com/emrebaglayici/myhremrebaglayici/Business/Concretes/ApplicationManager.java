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
import com.emrebaglayici.myhremrebaglayici.Repository.ApplicationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

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
            throw new NotFountException("Not found the job ads.");
        }
        Optional<User> applyUser = userCheckService.getUserById(dto.toApply().getUserId());
        User user = applyUser.orElseThrow(() -> new NotFountException("User with id : " + dto.toApply().getUserId() + " is not found"));
        if (userCheckService.checkHr(dto.toApply().getUserId())) {
            throw new PermissionException("Hrs cannot apply job ads");
        }
        if (Objects.equals(this.applicationRepository.getUserIdByJobId(dto.toApply().getJobId()), dto.toApply().getUserId()))
            throw new AlreadyCreatedException("This user already applied this ad.");
        this.applicationRepository.save(dto.toApply());
    }

    @Override
    public Page<Application> listApply(Pageable pageable) {
        return this.applicationRepository.findAll(pageable);
    }

    @Override
    public Application updateExperienceYear(Long id, Long userId, int experienceYear) {
        Optional<Application> applyOptional = this.applicationRepository.findById(id);
        Application apply = applyOptional.orElseThrow(() -> new NotFountException("Apply not found!"));
        Optional<User> userOptional = this.userCheckService.getUserById(userId);
        User user = userOptional.orElseThrow(() -> new NotFountException("User not found!"));
        if (!this.userCheckService.checkCandidates(userId))
            throw new PermissionException("User must be candidate!");
        if (!Objects.equals(apply.getUserId(), userId)) {
            throw new NotFountException("User not found!");
        }
        apply.setExperienceYear(experienceYear);
        this.applicationRepository.save(apply);
        return apply;
    }

    @Override
    public Application updatePersonalInfo(Long id, Long userId, String personalInfo) {
        Optional<Application> applyOptional = this.applicationRepository.findById(id);
        Application apply = applyOptional.orElseThrow(() -> new NotFountException("Apply not found!"));
        Optional<User> userOptional = this.userCheckService.getUserById(userId);
        User user = userOptional.orElseThrow(() -> new NotFountException("User not found!"));
        if (!this.userCheckService.checkCandidates(userId))
            throw new PermissionException("User must be candidate!");
        if (!Objects.equals(apply.getUserId(), userId)) {
            throw new NotFountException("User not found!");
        }
        apply.setPersonalInfo(personalInfo);
        this.applicationRepository.save(apply);
        return apply;
    }
}

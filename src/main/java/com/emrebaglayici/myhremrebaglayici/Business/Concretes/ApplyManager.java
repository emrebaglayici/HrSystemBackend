package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.ApplyService;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.JobAdvertisementCheckService;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.UserCheckService;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.ApplyCreateDto;
import com.emrebaglayici.myhremrebaglayici.Core.*;
import com.emrebaglayici.myhremrebaglayici.Entities.Apply;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import com.emrebaglayici.myhremrebaglayici.Exceptions.NotFountException;
import com.emrebaglayici.myhremrebaglayici.Repository.ApplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplyManager implements ApplyService {

    private final ApplyRepository applyRepository;
    private final UserCheckService userCheckService;
    private final JobAdvertisementCheckService jobAdvertisementCheckService;

    public ApplyManager(ApplyRepository applyRepository,
                        UserCheckService userCheckService,
                        JobAdvertisementCheckService jobAdvertisementCheckService) {
        this.applyRepository = applyRepository;
        this.userCheckService = userCheckService;
        this.jobAdvertisementCheckService = jobAdvertisementCheckService;
    }

    @Override
    public void applyJob(ApplyCreateDto dto) {
        if (jobAdvertisementCheckService.count() != 0) {
            if (!jobAdvertisementCheckService.existsJob(dto.toApply().getJobId())) {
                throw new NotFountException("Not found the job ads.");
            }
        }
        Optional<User> applyUser = userCheckService.getUserById(dto.toApply().getUserId());
        User user = applyUser.orElseThrow(() -> new NotFountException("User with id : " + dto.toApply().getUserId() + " is not found"));
        if (userCheckService.checkHr(dto.toApply().getUserId())) {
            throw new NotFountException("Hrs cannot apply job ads");
        }
        if (this.applyRepository.getUserIdByJobId(dto.toApply().getJobId()) != dto.toApply().getUserId()) {
            this.applyRepository.save(dto.toApply());

        } else
            throw new NotFountException("This user already applied this ad.");

    }

    @Override
    public Page<Apply> listApply(Pageable pageable) {
        return this.applyRepository.findAll(pageable);
    }

    @Override
    public Result updateExperienceYear(Long id, Long userId, int experienceYear) {
        Apply apply;
        if (this.applyRepository.existsById(id)) {
            if (userCheckService.existsUser(userId)) {
                if (this.userCheckService.checkCandidates(userId)) {
                    if (this.applyRepository.getUserIdById(id) == userId) {
                        apply = this.applyRepository.findById(id).get();
                        apply.setExperienceYear(experienceYear);
                        this.applyRepository.save(apply);
                        return new SuccessDataResult<>("Experience year updated");
                    } else {
                        return new ErrorResult("User not found");
                    }
                } else {
                    return new ErrorResult("Hr cannot edit this part");
                }
            } else {
                return new ErrorResult("User not exists");
            }
        } else {
            return new ErrorResult("Apply not found");
        }
    }

    @Override
    public Result updatePersonalInfo(Long id, Long userId, String personalInfo) {
        Apply apply;
        if (this.applyRepository.existsById(id)) {
            if (userCheckService.existsUser(userId)) {
                if (userCheckService.checkCandidates(userId)) {
                    if (this.applyRepository.getUserIdById(id) == userId) {
                        apply = this.applyRepository.findById(id).get();
                        apply.setPersonalInfo(personalInfo);
                        this.applyRepository.save(apply);
                        return new SuccessDataResult<>("Personal Info Changed");
                    } else {
                        return new ErrorResult("User not found");
                    }
                } else {
                    return new ErrorResult("Hr cannot edit this part");
                }
            } else {
                return new ErrorResult("User not exists");
            }
        } else {
            return new ErrorResult("Apply not found");
        }

    }
}

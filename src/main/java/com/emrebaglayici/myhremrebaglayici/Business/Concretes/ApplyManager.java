package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.ApplyService;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.JobAdvertisementCheckService;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.UserCheckService;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.ApplyCreateDto;
import com.emrebaglayici.myhremrebaglayici.Core.DataResult;
import com.emrebaglayici.myhremrebaglayici.Core.ErrorDataResult;
import com.emrebaglayici.myhremrebaglayici.Core.Result;
import com.emrebaglayici.myhremrebaglayici.Core.SuccessDataResult;
import com.emrebaglayici.myhremrebaglayici.Entities.Apply;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import com.emrebaglayici.myhremrebaglayici.NotFountException;
import com.emrebaglayici.myhremrebaglayici.Repository.ApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplyManager implements ApplyService {

    private final ApplyRepository applyRepository;
    private final UserCheckService userCheckService;
    private final JobAdvertisementCheckService jobAdvertisementCheckService;

    @Autowired
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
}

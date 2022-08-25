package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.ApplyService;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.JobAdvertisementCheckService;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.UserCheckService;
import com.emrebaglayici.myhremrebaglayici.Core.DataResult;
import com.emrebaglayici.myhremrebaglayici.Core.ErrorDataResult;
import com.emrebaglayici.myhremrebaglayici.Core.Result;
import com.emrebaglayici.myhremrebaglayici.Core.SuccessDataResult;
import com.emrebaglayici.myhremrebaglayici.Entities.Apply;
import com.emrebaglayici.myhremrebaglayici.Repository.ApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Apply applyJob(Apply apply) {

        //check if this job ad exist
        if (this.jobAdvertisementCheckService.existsJob(apply.getJobId())) {
            //check the user exists
            if (this.userCheckService.existsUser(apply.getUserId())) {
                //check if this user's role is candidate
                if (this.userCheckService.checkCandidates(apply.getUserId())) {
                    //check if this user already applied or not
                    if (this.applyRepository.getUserIdByJobId(apply.getJobId()) !=
                            apply.getUserId()) {
                        this.applyRepository.save(apply);
//                        return new SuccessDataResult(apply, "User applied to the job ad.");
                    } else
                        System.out.println("This user already applied this ad.");
                } else
                    System.out.println("Hr's cannot apply job ads");
            } else
                System.out.println("User not found");
        } else
            System.out.println("Job ads not found.");

        return apply;
    }
}

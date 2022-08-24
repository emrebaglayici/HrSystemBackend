package com.emrebaglayici.myhremrebaglayici.Controllers;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.JobAdvertisementService;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.JobAdvertisementCreateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.JobAdvertisementDto;
import com.emrebaglayici.myhremrebaglayici.Core.DataResult;
import com.emrebaglayici.myhremrebaglayici.Core.ErrorResult;
import com.emrebaglayici.myhremrebaglayici.Core.Result;
import com.emrebaglayici.myhremrebaglayici.Core.SuccessDataResult;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobAds")
public class JobAdvertisementController {
    private final JobAdvertisementService jobAdvertisementService;

    @Autowired
    public JobAdvertisementController(JobAdvertisementService jobAdvertisementService) {
        this.jobAdvertisementService = jobAdvertisementService;
    }

    @PostMapping("addJobAds")
    public Result create(@RequestBody JobAdvertisementCreateDto dto){
        DataResult<JobAdvertisement> jobAds=this.jobAdvertisementService.addJobAds(dto.toJobAds());
        if (jobAds.getData()!=null){
            return new SuccessDataResult<>(
                    JobAdvertisementDto.builder()
                            .id(jobAds.getData().getId())
                            .userId(jobAds.getData().getUserId())
                            .type(jobAds.getData().getType())
                            .description(jobAds.getData().getDescription())
                            .salary(jobAds.getData().getSalary())
                            .creationTime(jobAds.getData().getCreationTime())
                            .build(),"Success"
            );
        }else{
            return new ErrorResult("Data cannot be null");
        }
    }
    //Todo learn how to check errors and check create api

}

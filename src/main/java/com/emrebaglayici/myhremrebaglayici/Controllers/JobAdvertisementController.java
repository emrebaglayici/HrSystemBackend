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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobAds")
public class JobAdvertisementController {
    private final JobAdvertisementService jobAdvertisementService;

    @Autowired
    public JobAdvertisementController(JobAdvertisementService jobAdvertisementService) {
        this.jobAdvertisementService = jobAdvertisementService;
    }

    @PostMapping("addJobAds")
    public Result create(@RequestBody Long id,@RequestBody JobAdvertisementCreateDto dto){
        DataResult<JobAdvertisement> jobAds=this.jobAdvertisementService.addJobAds(id,dto.toJobAds());
        if (jobAds.getData()!=null){
            return new SuccessDataResult<>(
                    JobAdvertisementDto.builder()
                            .id(jobAds.getData().getId())
                            .user(jobAds.getData().getUser())
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

}

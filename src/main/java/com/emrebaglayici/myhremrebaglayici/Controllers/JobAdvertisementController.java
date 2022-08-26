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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    public Result create(@RequestBody JobAdvertisementCreateDto dto) {
        DataResult<JobAdvertisement> jobAds = this.jobAdvertisementService.addJobAds(dto.toJobAds());
        if (jobAds.getData() != null) {
            return new SuccessDataResult<>(
                    JobAdvertisementDto.builder()
                            .id(jobAds.getData().getId())
                            .userId(jobAds.getData().getUserId())
                            .type(jobAds.getData().getType())
                            .description(jobAds.getData().getDescription())
                            .salary(jobAds.getData().getSalary())
                            .creationTime(jobAds.getData().getCreationTime())
                            .build(), "Success"
            );
        } else {
            return new ErrorResult("Data cannot be null");
        }
    }
    //Todo learn how to check errors and check create api

    @GetMapping("jobAds")
    public Page<JobAdvertisementDto> listJobAds(Pageable pageable) {
        return jobAdvertisementService.listJobAds(pageable)
                .map(jobAdvertisement -> JobAdvertisementDto.builder()
                        .id(jobAdvertisement.getId())
                        .userId(jobAdvertisement.getUserId())
                        .type(jobAdvertisement.getType())
                        .description(jobAdvertisement.getDescription())
                        .salary(jobAdvertisement.getSalary())
                        .creationTime(jobAdvertisement.getCreationTime()).build());
    }

    @PutMapping("/updateJobAdsSalary")
    public Result updateJobAdSalary(@RequestParam Long id, @RequestParam Long userId, @RequestParam double salary) {
        return this.jobAdvertisementService.updateSalaryById(id, userId, salary);
    }

    @PutMapping("/updateJobAdsType")
    public Result updateJobAdType(@RequestParam Long id, @RequestParam Long userId, @RequestParam String type) {
        return this.jobAdvertisementService.updateTypeById(id, userId, type);
    }

    @PutMapping("/updateJobAdsDescription")
    public Result updateJobAdDescription(@RequestParam Long id, @RequestParam Long userId, @RequestParam String desc) {
        return this.jobAdvertisementService.updateDescriptionById(id, userId, desc);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestParam Long userId) {
        return ResponseEntity.ok(this.jobAdvertisementService.deleteById(id, userId));
    }


}

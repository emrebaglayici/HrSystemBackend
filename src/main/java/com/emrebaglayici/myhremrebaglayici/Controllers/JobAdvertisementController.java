package com.emrebaglayici.myhremrebaglayici.Controllers;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.JobAdvertisementService;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.JobAdvertisementCreateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.JobAdvertisementDto;
import com.emrebaglayici.myhremrebaglayici.Core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jobAds")
public class JobAdvertisementController {
    private final JobAdvertisementService jobAdvertisementService;

    @Autowired
    public JobAdvertisementController(JobAdvertisementService jobAdvertisementService) {
        this.jobAdvertisementService = jobAdvertisementService;
    }

    @PostMapping("jobAd")
    @ResponseStatus(HttpStatus.CREATED)
    public void createJobAd(@RequestBody JobAdvertisementCreateDto dto) {
        this.jobAdvertisementService.addJobAds(dto);
    }
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

    @PutMapping("/jobAdsSalary")
    public Result updateJobAdSalary(@RequestParam Long id, @RequestParam Long userId, @RequestParam double salary) {
        return this.jobAdvertisementService.updateSalaryById(id, userId, salary);
    }

    @PutMapping("/jobAdsType")
    public Result updateJobAdType(@RequestParam Long id, @RequestParam Long userId, @RequestParam String type) {
        return this.jobAdvertisementService.updateTypeById(id, userId, type);
    }

    @PutMapping("/jobAdsDescription")
    public Result updateJobAdDescription(@RequestParam Long id, @RequestParam Long userId, @RequestParam String desc) {
        return this.jobAdvertisementService.updateDescriptionById(id, userId, desc);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestParam Long userId) {
        return ResponseEntity.ok(this.jobAdvertisementService.deleteById(id, userId));
    }


}

package com.emrebaglayici.myhremrebaglayici.Controllers;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.JobAdvertisementService;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.JobAdvertisementCreateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.JobAdvertisementDto;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jobAds")
public class JobAdvertisementController {
    private final JobAdvertisementService jobAdvertisementService;

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

    @PatchMapping("/updateSalary/{id}/{userId}/{salary}")
    public JobAdvertisement updateJobAdSalary(@PathVariable Long id, @PathVariable Long userId, @PathVariable double salary) {
        return this.jobAdvertisementService.updateSalaryById(id, userId, salary);
    }

    @PatchMapping("/updateType/{id}/{userId}/{type}")
    public JobAdvertisement updateJobAdType(@PathVariable Long id, @PathVariable Long userId, @PathVariable String type) {
        return this.jobAdvertisementService.updateTypeById(id, userId, type);
    }

    @PatchMapping("/updateDesc/{id}/{userId}/{desc}")
    public JobAdvertisement updateJobAdDescription(@PathVariable Long id, @PathVariable Long userId, @PathVariable String desc) {
        return this.jobAdvertisementService.updateDescriptionById(id, userId, desc);
    }

    @PatchMapping("/updateActive/{id}/{userId}/{active}")
    public JobAdvertisement updateJobActive(@PathVariable Long id,@PathVariable Long userId,@PathVariable boolean active){
        return this.jobAdvertisementService.updateActive(id,userId,active);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestParam Long userId) {
        return ResponseEntity.ok(this.jobAdvertisementService.deleteById(id, userId));
    }


}

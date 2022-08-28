package com.emrebaglayici.myhremrebaglayici.Controllers;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.ApplicationService;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.ApplicationCreateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.ApplicationDto;
import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/apply")
public class ApplicationController {
    private final ApplicationService applicationService;
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("apply")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ApplicationCreateDto dto) {
        applicationService.applyJob(dto);
    }

    @GetMapping("applies")
    public Page<ApplicationDto> listApplies(Pageable pageable) {
        return applicationService.listApply(pageable).map(apply -> ApplicationDto.builder()
                .id(apply.getId())
                .userId(apply.getUserId())
                .jobId(apply.getJobId())
                .experienceYear(apply.getExperienceYear())
                .personalInfo(apply.getPersonalInfo())
                .appliedTime(apply.getAppliedTime()).build());
    }

    @PatchMapping("/experienceYear/{id}/{userId}/{experienceYear}")
    public Application updateApplyExperienceYear(@PathVariable Long id, @PathVariable Long userId, @PathVariable int experienceYear) {
        return this.applicationService.updateExperienceYear(id, userId, experienceYear);
    }

    @PatchMapping("/personalInfo/{id}/{userId}/{personalInfo}")
    public Application updateApplyPersonalInfo(@PathVariable Long id, @PathVariable Long userId, @PathVariable String personalInfo) {
        return this.applicationService.updatePersonalInfo(id, userId, personalInfo);
    }
}

package com.emrebaglayici.myhremrebaglayici.Controllers;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IApplication;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.ApplicationDtos.ApplicationCreateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.ApplicationDtos.ApplicationDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.ApplicationDtos.ApplicationUpdateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import com.emrebaglayici.myhremrebaglayici.Exceptions.NotFountException;
import com.emrebaglayici.myhremrebaglayici.Helper.Helper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/")
public class ApplicationController {
    private final IApplication iApplication;

    public ApplicationController(IApplication iApplication) {
        this.iApplication = iApplication;
    }

    @PostMapping("application")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ApplicationCreateDto dto) {
        iApplication.applyJob(dto);
    }

    @GetMapping("applications")
    public Page<ApplicationDto> listApplies(Pageable pageable) {
        return iApplication.listApply(pageable).map(apply -> ApplicationDto.builder()
                .id(apply.getId())
                .userId(apply.getUserId())
                .jobId(apply.getJobId())
                .experienceYear(apply.getExperienceYear())
                .personalInfo(apply.getPersonalInfo())
                .appliedTime(apply.getAppliedTime()).build());
    }

    @PatchMapping("updateApplication/{id}/{userId}")
    public ApplicationDto update(@PathVariable Long id, @PathVariable Long userId, @RequestBody ApplicationUpdateDto dto) {
        Application application = iApplication.getApplicationById(id).orElseThrow(() -> new NotFountException(Helper.JOB_ADVERTISEMENT_NOT_FOUND));

        boolean needUpdate = false;
        if (StringUtils.hasLength(dto.toApply().getPersonalInfo())) {
            application.setPersonalInfo(dto.toApply().getPersonalInfo());
            needUpdate = true;
        }
        if (dto.toApply().getExperienceYear() != application.getExperienceYear()
                && dto.toApply().getExperienceYear() != 0) {
            application.setExperienceYear(dto.toApply().getExperienceYear());
            needUpdate = true;
        }
        if (needUpdate)
            iApplication.update(id, userId, application);
        return ApplicationDto.builder()
                .id(id)
                .userId(userId)
                .jobId(application.getJobId())
                .experienceYear(dto.toApply().getExperienceYear())
                .personalInfo(dto.toApply().getPersonalInfo())
                .appliedTime(LocalDateTime.now())
                .build();
    }
}

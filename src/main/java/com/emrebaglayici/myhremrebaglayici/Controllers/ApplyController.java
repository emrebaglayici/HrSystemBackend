package com.emrebaglayici.myhremrebaglayici.Controllers;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.ApplyService;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.ApplyCreateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.ApplyDto;
import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/apply")
public class ApplyController {
    private final ApplyService applyService;
    public ApplyController(ApplyService applyService) {
        this.applyService = applyService;
    }

    @PostMapping("apply")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ApplyCreateDto dto) {
        applyService.applyJob(dto);
    }

    @GetMapping("applies")
    public Page<ApplyDto> listApplies(Pageable pageable) {
        return applyService.listApply(pageable).map(apply -> ApplyDto.builder()
                .id(apply.getId())
                .userId(apply.getUserId())
                .jobId(apply.getJobId())
                .experienceYear(apply.getExperienceYear())
                .personalInfo(apply.getPersonalInfo())
                .appliedTime(apply.getAppliedTime()).build());
    }

    @PatchMapping("/experienceYear/{id}/{userId}/{experienceYear}")
    public Application updateApplyExperienceYear(@PathVariable Long id, @PathVariable Long userId, @PathVariable int experienceYear) {
        return this.applyService.updateExperienceYear(id, userId, experienceYear);
    }

    @PatchMapping("/personalInfo/{id}/{userId}/{personalInfo}")
    public Application updateApplyPersonalInfo(@PathVariable Long id, @PathVariable Long userId, @PathVariable String personalInfo) {
        return this.applyService.updatePersonalInfo(id, userId, personalInfo);
    }
}

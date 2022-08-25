package com.emrebaglayici.myhremrebaglayici.Controllers;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.ApplyService;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.ApplyCreateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.ApplyDto;
import com.emrebaglayici.myhremrebaglayici.Core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/apply")
public class ApplyController {
    private final ApplyService applyService;

    @Autowired
    public ApplyController(ApplyService applyService) {
        this.applyService = applyService;
    }

    @PostMapping("applyJob")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ApplyCreateDto dto) {
        applyService.applyJob(dto);

    }

    @GetMapping("applies")
    public Page<ApplyDto> listApplies(Pageable pageable){
        return applyService.listApply(pageable).map(apply->ApplyDto.builder()
                .id(apply.getId())
                .userId(apply.getUserId())
                .jobId(apply.getJobId())
                .experienceYear(apply.getExperienceYear())
                .personalInfo(apply.getPersonalInfo())
                .appliedTime(apply.getAppliedTime()).build());
    }
    @PutMapping("/updateExperienceYear")
    public Result updateApplyExperienceYear(@RequestParam Long id,@RequestParam Long userId,@RequestParam int experienceYear){
        return this.applyService.updateExperienceYear(id,userId,experienceYear);
    }
}

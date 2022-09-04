package com.emrebaglayici.myhremrebaglayici.Controllers;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IJobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.JobAdsDtos.JobAdvertisementCreateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.JobAdsDtos.JobAdvertisementDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.JobAdsDtos.JobAdvertisementUpdateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Exceptions.NotFountException;
import com.emrebaglayici.myhremrebaglayici.Helper.Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class JobAdvertisementController {
    private final IJobAdvertisement IJobAdvertisement;

    public JobAdvertisementController(IJobAdvertisement IJobAdvertisement) {
        this.IJobAdvertisement = IJobAdvertisement;

    }

    @PostMapping("jobAdvertisement")
    @ResponseStatus(HttpStatus.CREATED)
    public void createJobAd(@RequestBody JobAdvertisementCreateDto dto) {
        this.IJobAdvertisement.addJobAds(dto);
    }

    @GetMapping("jobAdvertisements")
    public Page<JobAdvertisementDto> listJobAds(Pageable pageable) {
        return IJobAdvertisement.listJobAds(pageable)
                .map(jobAdvertisement -> JobAdvertisementDto.builder()
                        .id(jobAdvertisement.getId())
                        .userId(jobAdvertisement.getUserId())
                        .type(jobAdvertisement.getType())
                        .description(jobAdvertisement.getDescription())
                        .salary(jobAdvertisement.getSalary())
                        .interviewCount(jobAdvertisement.getInterviewCount())
                        .build());
    }

    @PatchMapping("/updateJobAd/{id}/{userId}")
    public JobAdvertisementDto update(@PathVariable Long id, @PathVariable Long userId, @RequestBody JobAdvertisementUpdateDto dto) {
        JobAdvertisement jobAdvertisement = IJobAdvertisement.findById(id).orElseThrow(() -> new NotFountException(Helper.JOB_ADVERTISEMENT_NOT_FOUND));

        if (dto.toJobAds().getInterviewCount() > 5 || dto.toJobAds().getInterviewCount() == 0)
            throw new NotFountException(Helper.INTERVIEW_COUNT_MUST_BE_1TO5);

        boolean needUpdate = false;
        if (StringUtils.hasLength(dto.toJobAds().getType())) {
            jobAdvertisement.setType(dto.toJobAds().getType());
            needUpdate = true;
        }
        if (StringUtils.hasLength(dto.toJobAds().getDescription())) {
            jobAdvertisement.setDescription(dto.toJobAds().getDescription());
            needUpdate = true;
        }
        if (dto.toJobAds().getSalary() != jobAdvertisement.getSalary() && dto.toJobAds().getSalary() != 0.0) {
            jobAdvertisement.setSalary(dto.toJobAds().getSalary());
            needUpdate = true;
        }
        if (dto.toJobAds().isActive() != jobAdvertisement.isActive()) {
            jobAdvertisement.setActive(dto.toJobAds().isActive());
            needUpdate = true;
        }
        if (dto.toJobAds().getInterviewCount() != jobAdvertisement.getInterviewCount()) {
            jobAdvertisement.setInterviewCount(dto.toJobAds().getInterviewCount());
            needUpdate = true;
        }
        if (needUpdate)
            IJobAdvertisement.update(id, userId, jobAdvertisement);

        return JobAdvertisementDto.builder()
                .id(id)
                .userId(userId)
                .type(jobAdvertisement.getType())
                .description(jobAdvertisement.getDescription())
                .salary(jobAdvertisement.getSalary())
                .active(jobAdvertisement.isActive())
                .interviewCount(jobAdvertisement.getInterviewCount())
                .build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestParam Long userId) {
        return ResponseEntity.ok(this.IJobAdvertisement.deleteById(id, userId));
    }
}

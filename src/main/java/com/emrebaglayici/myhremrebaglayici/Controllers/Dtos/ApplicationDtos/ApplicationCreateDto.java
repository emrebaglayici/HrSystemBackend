package com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.ApplicationDtos;

import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class ApplicationCreateDto {
    private Long jobId;
    private Long userId;
    private int experienceYear;
    private String personalInfo;

    public Application toApply() {
        return Application.builder()
                .jobId(this.jobId)
                .userId(this.userId)
                .experienceYear(this.experienceYear)
                .personalInfo(this.personalInfo)
                .appliedTime(LocalDateTime.now().withNano(0))
                .build();
    }
}

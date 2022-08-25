package com.emrebaglayici.myhremrebaglayici.Controllers.Dto;

import com.emrebaglayici.myhremrebaglayici.Entities.Apply;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class ApplyCreateDto {
    private Long jobId;
    private Long userId;
    private int experienceYear;
    private String personalInfo;
    private LocalDateTime appliedTime=LocalDateTime.now();

    public Apply toApply(){
        return Apply.builder()
                .jobId(this.jobId)
                .userId(this.userId)
                .experienceYear(this.experienceYear)
                .personalInfo(this.personalInfo)
                .appliedTime(this.appliedTime)
                .build();
    }
}

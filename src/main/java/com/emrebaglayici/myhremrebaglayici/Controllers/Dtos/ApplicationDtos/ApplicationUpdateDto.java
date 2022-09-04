package com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.ApplicationDtos;

import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class ApplicationUpdateDto {
    private int experienceYear;
    private String personalInfo;

    public Application toApply() {
        return Application.builder()
                .experienceYear(this.experienceYear)
                .personalInfo(this.personalInfo)
                .appliedTime(LocalDateTime.now())
                .build();
    }
}

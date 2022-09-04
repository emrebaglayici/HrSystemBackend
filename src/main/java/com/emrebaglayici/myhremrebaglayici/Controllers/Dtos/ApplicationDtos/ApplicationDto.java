package com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.ApplicationDtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApplicationDto {
    private Long id;
    private Long jobId;
    private Long userId;
    private int experienceYear;
    private String personalInfo;
    private LocalDateTime appliedTime = LocalDateTime.now();
}

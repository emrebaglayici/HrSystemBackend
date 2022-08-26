package com.emrebaglayici.myhremrebaglayici.Controllers.Dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApplyDto {
    private Long id;
    private Long jobId;
    private Long userId;
    private int experienceYear;
    private String personalInfo;
    private LocalDateTime appliedTime = LocalDateTime.now();
}

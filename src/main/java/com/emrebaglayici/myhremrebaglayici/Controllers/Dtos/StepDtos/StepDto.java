package com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.StepDtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StepDto {
    private Integer id;
    private String name;
    private Integer orderCount;
    private Long applicationId;
    private boolean result;
    private String notes;
}

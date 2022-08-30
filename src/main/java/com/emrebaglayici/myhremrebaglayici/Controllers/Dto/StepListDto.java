package com.emrebaglayici.myhremrebaglayici.Controllers.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StepListDto {
    private Integer id;
    private String name;
    private Long applicationId;
    private boolean result;
    private String notes;
}

package com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.JobAdsDtos;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class JobAdvertisementDto {
    private Long id;
    private Long userId;
    private String type;
    private String description;
    private double salary;
    private boolean active;
    private int interviewCount;
}

package com.emrebaglayici.myhremrebaglayici.Controllers.Dto;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder

public class JobAdvertisementDto {
    private Long id;
    private Long userId;
    private String type;
    private String description;
    private double salary;
    private double active;
    private int interviewCount;
    private LocalDateTime creationTime = LocalDateTime.now();
}

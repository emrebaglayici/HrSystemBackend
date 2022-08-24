package com.emrebaglayici.myhremrebaglayici.Controllers.Dto;

import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
public class JobAdvertisementCreateDto {
    private Long userId;
    private String type;
    private String description;
    private double salary;
    private LocalDate creationTime=LocalDate.now();

    public JobAdvertisement toJobAds(){
        return JobAdvertisement.builder()
                .userId(this.userId)
                .type(this.type)
                .description(this.description)
                .salary(this.salary)
                .creationTime(this.creationTime)
                .build();
    }
}
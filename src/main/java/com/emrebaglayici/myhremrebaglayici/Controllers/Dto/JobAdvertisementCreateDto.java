package com.emrebaglayici.myhremrebaglayici.Controllers.Dto;

import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class JobAdvertisementCreateDto {
    private User user;
    private String type;
    private String description;
    private double salary;
    private LocalDateTime creationTime=LocalDateTime.now();

    public JobAdvertisement toJobAds(){
        return JobAdvertisement.builder()
                .user(this.user)
                .type(this.type)
                .description(this.description)
                .salary(this.salary)
                .creationTime(this.creationTime)
                .build();
    }
}

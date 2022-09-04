package com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.JobAdsDtos;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
public class JobAdvertisementCreateDto {
    private Long userId;
    private String type;
    private String description;
    private double salary;
    private boolean active;
    private int interviewCount;

    public JobAdvertisement toJobAds() {
        return JobAdvertisement.builder()
                .userId(this.userId)
                .type(this.type)
                .description(this.description)
                .salary(this.salary)
                .active(this.active)
                .interviewCount(this.interviewCount)
                .creationTime(LocalDateTime.now())
                .build();
    }
}
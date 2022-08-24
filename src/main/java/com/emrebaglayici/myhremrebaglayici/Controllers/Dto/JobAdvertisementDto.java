package com.emrebaglayici.myhremrebaglayici.Controllers.Dto;

import com.emrebaglayici.myhremrebaglayici.Entities.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class JobAdvertisementDto {
    private Long id;
    private User user;
    private String type;
    private String description;
    private double salary;
    private LocalDateTime creationTime=LocalDateTime.now();
}

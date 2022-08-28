package com.emrebaglayici.myhremrebaglayici.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long jobId;
    private Long userId;
    private int experienceYear;
    private String personalInfo;
    private boolean jobAdsActive;
    private LocalDateTime appliedTime = LocalDateTime.now();

}

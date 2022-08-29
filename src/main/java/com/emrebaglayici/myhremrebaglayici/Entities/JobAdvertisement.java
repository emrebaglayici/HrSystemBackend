package com.emrebaglayici.myhremrebaglayici.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobAdvertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private String type;
    private String description;
    private double salary;
    private boolean active;
    private int interviewCount;
    private LocalDateTime creationTime = LocalDateTime.now();

}

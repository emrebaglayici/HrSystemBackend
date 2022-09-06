package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Repository.JobAdvertisementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobAdvertisementCheckTest {

    @Mock
    private JobAdvertisementRepository mockRepo;

    @InjectMocks
    private JobAdvertisementCheckManager jobAdvertisementCheckManager;

    @Test
    void existsJobTest(){
        JobAdvertisement jobAds = new JobAdvertisement(
                1L, 2L, "Full-Time", "Backend Dev",
                10000, true, 3, LocalDateTime.now());
        when(mockRepo.findById(jobAds.getId())).thenReturn(Optional.of(jobAds));
        jobAdvertisementCheckManager.existsJob(jobAds.getId());
        verify(mockRepo).findById(jobAds.getId());
    }

    @Test
    void isActiveTest(){
        JobAdvertisement jobAds = new JobAdvertisement(
                1L, 2L, "Full-Time", "Backend Dev",
                10000, true, 3, LocalDateTime.now());
        when(mockRepo.findById(jobAds.getId())).thenReturn(Optional.of(jobAds));
        jobAdvertisementCheckManager.isActive(jobAds.getId());
        verify(mockRepo).findById(jobAds.getId());
    }

    @Test
    void getJobById(){
        JobAdvertisement jobAds = new JobAdvertisement(
                1L, 2L, "Full-Time", "Backend Dev",
                10000, true, 3, LocalDateTime.now());
        when(mockRepo.findById(jobAds.getId())).thenReturn(Optional.of(jobAds));
        jobAdvertisementCheckManager.getJobById(jobAds.getId());
        verify(mockRepo).findById(jobAds.getId());
    }
}

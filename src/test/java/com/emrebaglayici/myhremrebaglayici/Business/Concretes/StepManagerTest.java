package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IJobAdvertisementCheck;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.StepDtos.StepCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Entities.Step;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import com.emrebaglayici.myhremrebaglayici.Repository.ApplicationRepository;
import com.emrebaglayici.myhremrebaglayici.Repository.JobAdvertisementRepository;
import com.emrebaglayici.myhremrebaglayici.Repository.StepRepository;
import com.emrebaglayici.myhremrebaglayici.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StepManagerTest {

    @Mock
    private StepRepository mockStepRepo;

    @Mock
    private ApplicationCheckManager applicationCheckManager;


    @Mock
    private UserRepository mockUserRepo;

    @Mock
    private JobAdvertisementRepository mockJobAdsRepo;

    @Mock
    private ApplicationRepository mockAppRepo;

    @Mock
    private IJobAdvertisementCheck iJobAdvertisementCheck;

    @InjectMocks
    private StepManager underTest;

    @Test
    void listStepsTest() {
        Pageable pageable = PageRequest.of(1, 20, Sort.by("id"));
        List<Step> stepList = new ArrayList<>();
        Step step = new Step(1, "Hr", 1, 2L, true, "Passed");
        stepList.add(step);
        when(mockStepRepo.findAll(pageable)).thenReturn(new PageImpl<>(stepList));
        assertEquals(1, underTest.listStep(pageable).getContent().size());
    }

    @Test
    void createStepTest() {
        User user = new User(1L, "Emre", "Hr");
        mockUserRepo.save(user);
        JobAdvertisement jobAds = new JobAdvertisement(
                2L, user.getId(), "Full-Time", "Backend Dev",
                10000, true, 3, LocalDateTime.now());
        mockJobAdsRepo.save(jobAds);
        Application application = new Application(
                1L, 3L, 2L, 10, "I am very good", LocalDateTime.now()
        );
        mockAppRepo.save(application);
        StepCreateDto dto = new StepCreateDto();
        dto.setName("Hr");
        dto.setResult(true);
        dto.setNotes("Hr Interview");
        dto.setApplicationId(2L);
        mockStepRepo.save(dto.toStep());
        verify(mockStepRepo, times(1)).save(dto.toStep());
    }

    @Test
    void updateStep(){
        User user = new User(1L, "Emre", "Hr");
        mockUserRepo.save(user);
        JobAdvertisement jobAds = new JobAdvertisement(
                2L, user.getId(), "Full-Time", "Backend Dev",
                10000, true, 3, LocalDateTime.now());
        mockJobAdsRepo.save(jobAds);
        Application application = new Application(
                3L, 2L, 1L, 10, "I am very good", LocalDateTime.now()
        );
        mockAppRepo.save(application);
        Step step=new Step(1,"OFFER",2,3L,true,"Passed");
        mockStepRepo.save(step);
        when(mockStepRepo.findById(step.getId())).thenReturn(Optional.of(step));
        when(applicationCheckManager.findById(application.getId())).thenReturn(Optional.of(application));
        when(iJobAdvertisementCheck.getJobById(application.getJobId())).thenReturn(Optional.of(jobAds));
        underTest.updateStep(step);
        verify(mockStepRepo,times(2)).save(step);
    }
}

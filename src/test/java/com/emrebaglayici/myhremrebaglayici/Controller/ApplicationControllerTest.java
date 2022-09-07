package com.emrebaglayici.myhremrebaglayici.Controller;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IApplication;
import com.emrebaglayici.myhremrebaglayici.Controllers.ApplicationController;
import com.emrebaglayici.myhremrebaglayici.Controllers.CustomError;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.ApplicationDtos.ApplicationCreateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.ApplicationDtos.ApplicationDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.ApplicationDtos.ApplicationUpdateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import com.emrebaglayici.myhremrebaglayici.Repository.ApplicationRepository;
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
class ApplicationControllerTest {
    @Mock
    private IApplication iApplication;

    @Mock
    private ApplicationRepository mockAppRepo;

    @InjectMocks
    private ApplicationController underTest;

    @Test
    void createTest(){
        ApplicationCreateDto dto=new ApplicationCreateDto();
        dto.setExperienceYear(10);
        dto.setUserId(1L);
        dto.setPersonalInfo("good");
        dto.setJobId(3L);
        underTest.create(dto);
        verify(iApplication,times(1)).applyJob(dto);
    }

    @Test
    void listAppliesTest(){
        Pageable pageable= PageRequest.of(1,20, Sort.by("id"));
        List<Application> applicationList=new ArrayList<>();
        Application application=new Application(1L,2L,3L,10,"good", LocalDateTime.now());
        applicationList.add(application);
        when(iApplication.listApply(pageable)).thenReturn(new PageImpl<>(applicationList));
        assertEquals(1,underTest.listApplies(pageable).getContent().size());
    }

    @Test
    void updateTest(){
        Application application=new Application(1L,2L,3L,10,"good", LocalDateTime.now());
        mockAppRepo.save(application);
        ApplicationUpdateDto dto =new ApplicationUpdateDto();
        dto.setExperienceYear(25);
        when(iApplication.getApplicationById(application.getId())).thenReturn(Optional.of(application));
        underTest.update(application.getId(),application.getUserId(),dto);
        verify(mockAppRepo,times(1)).save(application);
    }

}

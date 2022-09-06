package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IJobAdvertisementCheck;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IUserCheck;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.ApplicationDtos.ApplicationCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import com.emrebaglayici.myhremrebaglayici.Repository.ApplicationRepository;
import com.emrebaglayici.myhremrebaglayici.Repository.JobAdvertisementRepository;
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
class ApplicationManagerTest {
    @Mock
    private ApplicationRepository mockAppRepo;
    @Mock
    private IUserCheck iUserCheck;
    @Mock
    private IJobAdvertisementCheck iJobAdvertisementCheck;
    @Mock
    private JobAdvertisementRepository mockJobAdRepo;

    @Mock
    private UserRepository mockUserRepo;
    @InjectMocks
    private ApplicationManager underTest;

    @Test
    void applyJob(){
        User user=new User(1L,"Emre","Candidates");
        mockUserRepo.save(user);
        JobAdvertisement jobAds = new JobAdvertisement(
                2L, user.getId(), "Full-Time", "Backend Dev",
                10000, true, 3, LocalDateTime.now());
        mockJobAdRepo.save(jobAds);
        System.out.println(jobAds);
        ApplicationCreateDto dto=new ApplicationCreateDto();
        dto.setUserId(user.getId());
        dto.setJobId(jobAds.getId());
        dto.setPersonalInfo("selam ben emre");
        dto.setExperienceYear(20);
        when(iUserCheck.getUserById(user.getId())).thenReturn(Optional.of(user));
        when(iJobAdvertisementCheck.existsJob(dto.toApply().getJobId())).thenReturn(true);
        when(iJobAdvertisementCheck.isActive(dto.toApply().getJobId())).thenReturn(true);
        underTest.applyJob(dto);
        verify(mockAppRepo,times(1)).save(dto.toApply());
    }

    @Test
    void listApplyTest(){
        Pageable pageable= PageRequest.of(1,20, Sort.by("id"));
        List<Application> applicationList=new ArrayList<>();
        Application application=new Application(
                1L,3L,2L,10,"I am very good",LocalDateTime.now()
        );
        applicationList.add(application);
        when(mockAppRepo.findAll(pageable)).thenReturn(new PageImpl<>(applicationList));
        assertEquals(1,underTest.listApply(pageable).getContent().size());
    }

    @Test
    void getApplicationById(){
        Application application=new Application(
                1L,3L,2L,10,"I am very good",LocalDateTime.now()
        );
        when(mockAppRepo.findById(application.getId())).thenReturn(Optional.of(application));
        underTest.getApplicationById(application.getId());
        verify(mockAppRepo).findById(application.getId());
    }

    @Test
    void updateTest(){
        User user=new User(1L,"Emre","Candidates");
        mockUserRepo.save(user);
        JobAdvertisement jobAds = new JobAdvertisement(
                2L, user.getId(), "Full-Time", "Backend Dev",
                10000, true, 3, LocalDateTime.now());
        mockJobAdRepo.save(jobAds);
        Application application=new Application(
                3L, jobAds.getId(), user.getId(), 10,"I am very good",LocalDateTime.now()
        );
        mockAppRepo.save(application);
        when(iUserCheck.getUserById(user.getId())).thenReturn(Optional.of(user));
        underTest.update(3L, user.getId(), application);
        verify(mockAppRepo,times(2)).save(application);
    }
}

package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IJobAdvertisementCheck;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IUserCheck;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.ApplicationDtos.ApplicationCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Entities.Role;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import com.emrebaglayici.myhremrebaglayici.Exceptions.AlreadyCreatedException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.NotFountException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.PermissionException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void shouldReturnNotFoundExceptionWhenJobIdNotValidTryingToApplyJob(){
        ApplicationCreateDto dto=new ApplicationCreateDto();
        dto.setJobId(1L);
        assertThrows(NotFountException.class,()->underTest.applyJob(dto));
    }

    @Test
    void shouldReturnNotFoundExceptionWhenJobNotActiveTryingToApplyJob(){
        ApplicationCreateDto dto=new ApplicationCreateDto();
        dto.setJobId(1L);
        when(iJobAdvertisementCheck.existsJob(dto.toApply().getJobId())).thenReturn(true);
        assertThrows(NotFountException.class, ()->underTest.applyJob(dto));
    }

    @Test
    void shouldReturnPermissionExceptionWhenUserIdNotValidTryingToApplyToJob(){
        ApplicationCreateDto dto=new ApplicationCreateDto();
        dto.setJobId(1L);
        when(iJobAdvertisementCheck.existsJob(dto.toApply().getJobId())).thenReturn(true);
        when(iJobAdvertisementCheck.isActive(dto.toApply().getJobId())).thenReturn(true);
        assertThrows(NotFountException.class,()->underTest.applyJob(dto));
    }

    @Test
    void shouldReturnPermissionExceptionWhenHrTryToApplyJobAd(){
        ApplicationCreateDto dto=new ApplicationCreateDto();
        User user=new User(1L,"Emre","Hr");
        mockUserRepo.save(user);
        dto.setJobId(1L);
        dto.setUserId(user.getId());
        when(iJobAdvertisementCheck.existsJob(dto.toApply().getJobId())).thenReturn(true);
        when(iJobAdvertisementCheck.isActive(dto.toApply().getJobId())).thenReturn(true);
        when(iUserCheck.getUserById(dto.toApply().getUserId())).thenReturn(Optional.of(user));
        assertThrows(PermissionException.class,()->underTest.applyJob(dto));
    }


    @Test
    void shouldReturnAlreadyCreatedExceptionWhenUserAlreadyAppliedApplication(){
        ApplicationCreateDto dto=new ApplicationCreateDto();
        User user=new User(1L,"Emre", Role.CANDIDATES.getName());
        mockUserRepo.save(user);
        dto.setUserId(user.getId());
        dto.setJobId(3L);
        dto.setUserId(user.getId());
        dto.setPersonalInfo("Hi");
        dto.setExperienceYear(10);
        Application application=new Application(
                5L,dto.toApply().getJobId(), user.getId(), 10,"I am very good",LocalDateTime.now()
        );
        when(iJobAdvertisementCheck.existsJob(dto.toApply().getJobId())).thenReturn(true);
        when(iJobAdvertisementCheck.isActive(dto.toApply().getJobId())).thenReturn(true);
        when(iUserCheck.getUserById(dto.toApply().getUserId())).thenReturn(Optional.of(user));
        mockAppRepo.save(application);
        when(mockAppRepo.getUserIdByJobId(application.getJobId())).thenReturn(1L);
        assertThrows(AlreadyCreatedException.class,()->underTest.applyJob(dto));
    }

    @Test
    void shouldReturnNotFoundExceptionWhenUserNotValidTryingToUpdateApplication(){
        Application application=new Application(
                5L,2L, 3L, 10,"I am very good",LocalDateTime.now()
        );
        mockAppRepo.save(application);
        assertThrows(NotFountException.class,()->underTest.update(application.getId(),application.getUserId(),application));
    }

    @Test
    void shouldThrowPermissionExceptionWhenHrTryToUpdateApplication(){
        User user=new User(1L,"Emre", Role.HR.getName());
        Application application=new Application(
                5L,2L, user.getId(), 10,"I am very good",LocalDateTime.now()
        );
        mockAppRepo.save(application);
        when(iUserCheck.getUserById(application.getUserId())).thenReturn(Optional.of(user));
        assertThrows(PermissionException.class,()->underTest.update(application.getId(), user.getId(), application));
    }

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

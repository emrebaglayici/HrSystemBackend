package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IUserCheck;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.JobAdsDtos.JobAdvertisementCreateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.UserDtos.UserCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import com.emrebaglayici.myhremrebaglayici.Exceptions.FillTheBlanksException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.NotFountException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.PermissionException;
import com.emrebaglayici.myhremrebaglayici.Repository.JobAdvertisementRepository;
import com.emrebaglayici.myhremrebaglayici.Repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
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
class JobAdvertisementManagerTest {

    @InjectMocks
    private JobAdvertisementManager underTest;

    @Mock
    private JobAdvertisementRepository mockJobRepo;

    @Mock
    private IUserCheck iUserCheck;

    @Mock
    private IUserCheck mockIJob;

    @Mock
    private UserRepository mockUserRepo;

    @Test
    void shouldReturnNotFoundExceptionWhenInvalidUserIdTryingToAddJobAds(){
        JobAdvertisementCreateDto dto=new JobAdvertisementCreateDto();
        dto.setUserId(1L);
        assertThrows(NotFountException.class,()->underTest.addJobAds(dto));
    }

    @Test
    void shouldReturnPermissionExceptionWhenCandidateTryingToAddJobAds(){
        User user=new User(1L,"Emre","Candidate");
        mockUserRepo.save(user);
        JobAdvertisementCreateDto dto=new JobAdvertisementCreateDto();
        dto.setUserId(user.getId());
        when(iUserCheck.getUserById(user.getId())).thenReturn(Optional.of(user));
        assertThrows(PermissionException.class,()->underTest.addJobAds(dto));
    }
    @Test
    void shouldNotFoundExceptionWhenInterviewCountInvalidTryingToAddJobAds(){
        User user=new User(1L,"Emre","Hr");
        mockUserRepo.save(user);
        JobAdvertisementCreateDto dto=new JobAdvertisementCreateDto();
        dto.setUserId(user.getId());
        dto.setInterviewCount(0);
        when(iUserCheck.getUserById(user.getId())).thenReturn(Optional.of(user));
        assertThrows(NotFountException.class,()->underTest.addJobAds(dto));
    }

    @Test
    void shouldThrowFillTheBlanksExceptionWhenFieldsAreEmptyTryingToAddJobAds(){
        User user=new User(1L,"Emre","Hr");
        mockUserRepo.save(user);
        JobAdvertisementCreateDto dto=new JobAdvertisementCreateDto();
        dto.setUserId(user.getId());
        dto.setType("");
        dto.setInterviewCount(4);
        when(iUserCheck.getUserById(user.getId())).thenReturn(Optional.of(user));
        assertThrows(FillTheBlanksException.class,()->underTest.addJobAds(dto));
    }

    @Test
    void shouldReturnNotFountExceptionWhenUserNotValidTryingToUpdateJobAds(){
        User user=new User(1L,"Emre","Hr");
        JobAdvertisement jobAdvertisement=new JobAdvertisement(
            3L, user.getId(), "Full-Time","Back-End Developer",1000,true,5,LocalDateTime.now()
        );
        mockJobRepo.save(jobAdvertisement);
        assertThrows(NotFountException.class,()->underTest.update(jobAdvertisement.getId(), 123L, jobAdvertisement));
    }

    @Test
    void shouldReturnPermissionExceptionWhenNotCreatedUserTryingToUpdateJobAds(){
        User user=new User(1L,"Emre","Hr");
        mockUserRepo.save(user);
        JobAdvertisement jobAdvertisement=new JobAdvertisement(
                3L, 4L, "Full-Time","Back-End Developer",1000,true,5,LocalDateTime.now()
        );
        mockJobRepo.save(jobAdvertisement);
        when(iUserCheck.getUserById(user.getId())).thenReturn(Optional.of(user));
        assertThrows(PermissionException.class,()->underTest.update(jobAdvertisement.getId(), user.getId(), jobAdvertisement));
    }

    @Test
    void shouldReturnNotFoundExceptionWhenJobIdInvalidTryingToDeleteById(){
        User user=new User(1L,"Emre","Hr");
        mockUserRepo.save(user);
        JobAdvertisement jobAdvertisement=new JobAdvertisement(
                3L, 4L, "Full-Time","Back-End Developer",1000,true,5,LocalDateTime.now()
        );
        mockJobRepo.save(jobAdvertisement);
        assertThrows(NotFountException.class,()->underTest.deleteById(jobAdvertisement.getId(), user.getId()));
    }

    @Test
    void shouldReturnNotFoundExceptionWhenUserIdInvalidTryingToUpdateById(){
        User user=new User(1L,"Emre","Hr");
        mockUserRepo.save(user);
        JobAdvertisement jobAdvertisement=new JobAdvertisement(
                3L, 4L, "Full-Time","Back-End Developer",1000,true,5,LocalDateTime.now()
        );
        mockJobRepo.save(jobAdvertisement);
        when(mockJobRepo.findById(jobAdvertisement.getId())).thenReturn(Optional.of(jobAdvertisement));
        assertThrows(NotFountException.class,()->underTest.deleteById(jobAdvertisement.getId(), user.getId()));
    }

    @Test
    void shouldReturnNotFoundExceptionWhenTheCandidateTryingToDeleteJobAds(){
        User user=new User(1L,"Emre","Candidate");
        mockUserRepo.save(user);
        JobAdvertisement jobAdvertisement=new JobAdvertisement(
                3L, 4L, "Full-Time","Back-End Developer",1000,true,5,LocalDateTime.now()
        );
        mockJobRepo.save(jobAdvertisement);
        when(mockJobRepo.findById(jobAdvertisement.getId())).thenReturn(Optional.of(jobAdvertisement));
        when(iUserCheck.getUserById(user.getId())).thenReturn(Optional.of(user));
        assertThrows(NotFountException.class,()->underTest.deleteById(jobAdvertisement.getId(), user.getId()));
    }

    @Test
    void findByIdTest() {
        JobAdvertisement jobAds = new JobAdvertisement(
                1L, 2L, "Full-Time", "Backend Dev",
                10000, true, 3, LocalDateTime.now());
        when(mockJobRepo.findById(jobAds.getId())).thenReturn(Optional.of(jobAds));
        underTest.findById(jobAds.getId());
        verify(mockJobRepo).findById(jobAds.getId());
    }

    @Test
    void listJobAdsTest() {
        Pageable pageable = PageRequest.of(1, 20, Sort.by("id"));
        List<JobAdvertisement> jobAdvertisementList = new ArrayList<>();
        JobAdvertisement jobAds = new JobAdvertisement(
                1L, 2L, "Full-Time", "Backend Dev",
                10000, true, 3, LocalDateTime.now());
        jobAdvertisementList.add(jobAds);
        when(mockJobRepo.findAll(pageable)).thenReturn(new PageImpl<>(jobAdvertisementList));
        assertEquals(1, underTest.listJobAds(pageable).getContent().size());
    }

    @Test
    void updateTest() {
        User user = new User(1L, "Emre", "Hr");
        mockUserRepo.save(user);
        JobAdvertisement jobAds = new JobAdvertisement(
                2L, user.getId(), "Full-Time", "Backend Dev",
                10000, true, 3, LocalDateTime.now());
        mockJobRepo.save(jobAds);
        when(mockIJob.getUserById(jobAds.getUserId())).thenReturn(Optional.of(user));
        underTest.update(2L, user.getId(), jobAds);
        verify(mockJobRepo, times(2)).save(jobAds);
    }

    @Test
    void deleteByIdTest() {
        User user=new User(1L,"Emre","Hr");
        mockUserRepo.save(user);
        JobAdvertisement jobAds = new JobAdvertisement(
                2L, user.getId(), "Full-Time", "Backend Dev",
                10000, true, 3, LocalDateTime.now());
        mockJobRepo.save(jobAds);
        when(mockIJob.getUserById(jobAds.getUserId())).thenReturn(Optional.of(user));
        when(mockJobRepo.findById(jobAds.getId())).thenReturn(Optional.of(jobAds));
        underTest.deleteById(2L, user.getId());
        verify(mockJobRepo,times(1)).delete(jobAds);
    }

    @Test
    void addJobAdsTest(){
        User user=new User(1L,"Emre","Hr");
        mockUserRepo.save(user);
        JobAdvertisementCreateDto dto=new JobAdvertisementCreateDto();
        dto.setUserId(user.getId());
        dto.setType("Full Time");
        dto.setDescription("Back-end Developer");
        dto.setSalary(10000);
        dto.setActive(true);
        dto.setInterviewCount(3);
        when(mockIJob.getUserById(dto.toJobAds().getUserId())).thenReturn(Optional.of(user));
        underTest.addJobAds(dto);
        verify(mockJobRepo,times(1)).save(dto.toJobAds());
    }
}

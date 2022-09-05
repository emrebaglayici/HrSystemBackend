package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IUserCheck;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.JobAdsDtos.JobAdvertisementCreateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.UserDtos.UserCreateDto;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobAdvertisementManagerTest {

    @InjectMocks
    private JobAdvertisementManager jobAdvertisementManager;

    @Mock
    private JobAdvertisementRepository jobAdvertisementRepository;

    @Mock
    private IUserCheck iUserCheck;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private static UserManager userManager;

    @Test
    void shouldFindById(){
        jobAdvertisementRepository.findById(1L);
        verify(jobAdvertisementRepository).findById(1L);
    }

    @BeforeAll
    static void setUpUser(){
        UserCreateDto dto=new UserCreateDto();
        String name="Emre";
        String role="Hr";
        dto.setRole(role);
        dto.setName(name);
        userManager.saveUser(dto);
    }

    @Test
    void shouldReturnUserNotFoundException(){
        JobAdvertisementCreateDto dto=new JobAdvertisementCreateDto();
        Long userId=213213L;
        String type="Full-Time";
        String desc="Backend Dev";
        double salary=10000;
        boolean active=true;
        int interviewCount=3;
        dto.setActive(active);
        dto.setType(type);
        dto.setSalary(salary);
        dto.setDescription(desc);
        dto.setUserId(userId);
        dto.setInterviewCount(interviewCount);
        assertThrows(NotFountException.class,()->jobAdvertisementManager.addJobAds(dto));
    }
    @Test
    void shouldReturnUserMustBeHrException(){
        JobAdvertisementCreateDto dto=new JobAdvertisementCreateDto();
        Long userId=1L;
        String type="";
        String desc="";
        double salary=0;
        boolean active=true;
        int interviewCount=3;
        dto.setActive(active);
        dto.setType(type);
        dto.setSalary(salary);
        dto.setDescription(desc);
        dto.setUserId(userId);
        dto.setInterviewCount(interviewCount);

        assertThrows(FillTheBlanksException.class,()->jobAdvertisementManager.addJobAds(dto));
    }
}

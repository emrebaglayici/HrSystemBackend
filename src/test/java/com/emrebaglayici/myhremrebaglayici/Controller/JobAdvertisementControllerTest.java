package com.emrebaglayici.myhremrebaglayici.Controller;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IJobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.JobAdsDtos.JobAdvertisementCreateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.JobAdsDtos.JobAdvertisementUpdateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.JobAdvertisementController;
import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import com.emrebaglayici.myhremrebaglayici.Repository.JobAdvertisementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobAdvertisementControllerTest {
    @Mock
    private IJobAdvertisement iJobAdvertisement;

    @Mock
    private JobAdvertisementRepository mockJobRepo;
    @InjectMocks
    private JobAdvertisementController underTest;

    @Test
    void createJobAdTest(){
        JobAdvertisementCreateDto dto=new JobAdvertisementCreateDto();
        dto.setUserId(1L);
        dto.setType("full");
        dto.setActive(true);
        dto.setSalary(1000);
        dto.setDescription("aaa");
        dto.setInterviewCount(3);
        underTest.createJobAd(dto);
        verify(iJobAdvertisement,times(1)).addJobAds(dto);
    }

    @Test
    void listJobAdsTest(){
        Pageable pageable= PageRequest.of(1,20, Sort.by("id"));
        List<JobAdvertisement> jobAdvertisementList=new ArrayList<>();
        JobAdvertisement jobAds=new JobAdvertisement(1L,2L,"full","back",1000,true,3, LocalDateTime.now());
        jobAdvertisementList.add(jobAds);
        when(iJobAdvertisement.listJobAds(pageable)).thenReturn(new PageImpl<>(jobAdvertisementList));
        assertEquals(1,underTest.listJobAds(pageable).getContent().size());
    }

    @Test
    void updateTest(){
        JobAdvertisement jobAdvertisement=new JobAdvertisement(
                1L,2L,"Full","Desc",1000,true,3,LocalDateTime.now()
        );
        mockJobRepo.save(jobAdvertisement);
        JobAdvertisementUpdateDto dto=new JobAdvertisementUpdateDto();
        dto.setDescription("asd");
        dto.setInterviewCount(5);
        when(iJobAdvertisement.findById(jobAdvertisement.getId())).thenReturn(Optional.of(jobAdvertisement));
        underTest.update(jobAdvertisement.getId(), jobAdvertisement.getUserId(), dto);
        verify(mockJobRepo,times(1)).save(jobAdvertisement);
    }

    @Test
    void deleteTest(){
        JobAdvertisement jobAdvertisement=new JobAdvertisement(
                1L,2L,"Full","Desc",1000,true,3,LocalDateTime.now()
        );
        ResponseEntity<Object> responseEntity= (ResponseEntity<Object>) underTest.delete(jobAdvertisement.getId(), jobAdvertisement.getUserId());
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }
}

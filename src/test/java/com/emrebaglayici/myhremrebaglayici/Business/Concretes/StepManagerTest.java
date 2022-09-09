package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IJobAdvertisementCheck;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.StepDtos.StepCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.*;
import com.emrebaglayici.myhremrebaglayici.Exceptions.FillTheBlanksException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.InterviewFailException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.NotFountException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.PermissionException;
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

import static org.junit.jupiter.api.Assertions.*;
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
    void shouldThrowNotFoundExceptionWhenStepResultFalseTryingToCreateStep() {
        StepCreateDto dto = new StepCreateDto();
        dto.setName("Hr");
        dto.setResult(false);
        dto.setNotes("Hr Interview");
        dto.setApplicationId(2L);
        assertThrows(NotFountException.class, () -> underTest.createStep(dto));
    }

    @Test
    void shouldThrowFillTheBlanksExceptionWhenEmptyFieldsTryingToSave() {
        StepCreateDto dto = new StepCreateDto();
        dto.setName("");
        dto.setResult(true);
        dto.setNotes("");
        dto.setApplicationId(2L);
        assertThrows(FillTheBlanksException.class, () -> underTest.createStep(dto));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenNotValidApplicationTryingToSave() {
        StepCreateDto dto = new StepCreateDto();
        dto.setName("Hr");
        dto.setResult(true);
        dto.setNotes("Hr Interview");
        dto.setApplicationId(2L);
        assertThrows(NotFountException.class, () -> underTest.createStep(dto));

    }

    @Test
    void shouldThrowNotFoundExceptionWhenCandidateCreateStep() {
        Application application=new Application(
                5L,2L, 3L, 10,"I am very good",LocalDateTime.now()
        );
        mockAppRepo.save(application);
        StepCreateDto dto = new StepCreateDto();
        dto.setName(Role.CANDIDATES.getName());
        dto.setResult(true);
        dto.setNotes("Hr Interview");
        dto.setApplicationId(2L);
        when(applicationCheckManager.findById(dto.toStep().getApplicationId())).thenReturn(Optional.of(application));
        assertThrows(NotFountException.class,()->underTest.createStep(dto));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenStepNotValidTryToUpdateStep(){
        Step step=new Step(1,"CODETASK",2,2L,true,"Passed");
        assertThrows(NotFountException.class,()->underTest.updateStep(step));
    }
    @Test
    void shouldNotFoundExceptionWhenApplicationNotValidTryToUpdateStep(){
        Step step=new Step(1,"CODETASK",2,2L,true,"Passed");
        when(mockStepRepo.findById(step.getId())).thenReturn(Optional.of(step));
        assertThrows(NotFountException.class,()->underTest.updateStep(step));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenJobAdsNotValidTryToUpdateStep(){
        Step step=new Step(1,"CODETASK",2,2L,true,"Passed");
        Application application=new Application();
        when(mockStepRepo.findById(step.getId())).thenReturn(Optional.of(step));
        when(applicationCheckManager.findById(step.getApplicationId())).thenReturn(Optional.of(application));
        assertThrows(NotFountException.class,()->underTest.updateStep(step));
    }

    @Test
    void shouldThrowPermissionExceptionWhenSecondStepHrTryToUpdateStep(){
        Step step=new Step(1,"HR",2,2L,true,"Passed");
        Application application=new Application();
        JobAdvertisement jobAdvertisement=new JobAdvertisement();
        when(mockStepRepo.findById(step.getId())).thenReturn(Optional.of(step));
        when(applicationCheckManager.findById(step.getApplicationId())).thenReturn(Optional.of(application));
        when(iJobAdvertisementCheck.getJobById(application.getJobId())).thenReturn(Optional.of(jobAdvertisement));
        assertThrows(PermissionException.class,()->underTest.updateStep(step));
    }

    @Test
    void shouldThrowPermissionExceptionWhenOrderCountNotValidTryToUpdateStep(){
        Step step=new Step(1,"CODETASK",8,2L,true,"Passed");
        Application application=new Application();
        JobAdvertisement jobAdvertisement=new JobAdvertisement();
        when(mockStepRepo.findById(step.getId())).thenReturn(Optional.of(step));
        when(applicationCheckManager.findById(step.getApplicationId())).thenReturn(Optional.of(application));
        when(iJobAdvertisementCheck.getJobById(application.getJobId())).thenReturn(Optional.of(jobAdvertisement));
        assertThrows(PermissionException.class,()->underTest.updateStep(step));
    }

    @Test
    void shouldThrowInterviewFailExceptionWhenResultFalseTryToUpdateStep(){
        Step step=new Step(1,"CODETASK",2,2L,false,"Passed");
        Application application=new Application();
        JobAdvertisement jobAds = new JobAdvertisement(
                1L, 2L, "Full-Time", "Backend Dev",
                10000, true, 3, LocalDateTime.now());
        when(mockStepRepo.findById(step.getId())).thenReturn(Optional.of(step));
        when(applicationCheckManager.findById(step.getApplicationId())).thenReturn(Optional.of(application));
        when(iJobAdvertisementCheck.getJobById(application.getJobId())).thenReturn(Optional.of(jobAds));
        assertThrows(InterviewFailException.class,()->underTest.updateStep(step));
    }



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
    void updateStep() {
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
        Step step = new Step(1, "OFFER", 2, 3L, true, "Passed");
        mockStepRepo.save(step);
        when(mockStepRepo.findById(step.getId())).thenReturn(Optional.of(step));
        when(applicationCheckManager.findById(application.getId())).thenReturn(Optional.of(application));
        when(iJobAdvertisementCheck.getJobById(application.getJobId())).thenReturn(Optional.of(jobAds));
        underTest.updateStep(step);
        verify(mockStepRepo, times(2)).save(step);
    }
}

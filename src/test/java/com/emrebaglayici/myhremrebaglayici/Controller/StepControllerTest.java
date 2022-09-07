package com.emrebaglayici.myhremrebaglayici.Controller;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IStep;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IUser;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.StepDtos.StepCreateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.StepDtos.StepUpdateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.StepController;
import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import com.emrebaglayici.myhremrebaglayici.Entities.Step;
import com.emrebaglayici.myhremrebaglayici.Entities.Steps;
import com.emrebaglayici.myhremrebaglayici.Repository.StepRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StepControllerTest {
    @Mock
    private IStep iStep;

    @Mock
    private StepRepository mockStepRepo;
    @InjectMocks
    private StepController underTest;

    @Test
    void createTest(){
        StepCreateDto dto=new StepCreateDto();
        dto.setName("Hr");
        dto.setNotes("Passed");
        dto.setResult(true);
        dto.setApplicationId(2L);
        underTest.create(dto);
        verify(iStep,times(1)).createStep(dto);
    }

    @Test
    void listStepTest(){
        Pageable pageable= PageRequest.of(1,20, Sort.by("id"));
        List<Step> stepList=new ArrayList<>();
        Step step=new Step(
              1,"CODETASK",2,2L,true,"notes"
        );
        stepList.add(step);
        when(iStep.listStep(pageable)).thenReturn(new PageImpl<>(stepList));
        assertEquals(1,underTest.listStep(pageable).getContent().size());

    }

    @Test
    void updateStepTest(){
        Step step=new Step(
                4,"CODETASK",2,1L,true,"GIVEN"
        );
        Application application=new Application(1L,2L,3L,10,"good", LocalDateTime.now());
        mockStepRepo.save(step);
        StepUpdateDto dto=new StepUpdateDto();
        dto.setId(step.getId());
        dto.setNotes("PAIRPROGRAMMING DONE");
        dto.setName(Steps.PAIRPROGRAMMING.getName());
        dto.setResult(true);
        dto.setApplicationId(application.getId());
        verify(mockStepRepo,times(1)).save(step);
    }
}

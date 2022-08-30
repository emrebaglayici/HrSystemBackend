package com.emrebaglayici.myhremrebaglayici.Controllers;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.StepService;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.StepCreateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.StepDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.StepListDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.StepUpdateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/api/v1/step")
public class StepController {
    private final StepService stepService;

    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @PostMapping("step")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody StepCreateDto dto){
        this.stepService.createStep(dto);
    }

    @GetMapping("steps")
    public Page<StepListDto> listStep(Pageable pageable){
        return stepService.listStep(pageable)
                .map(step-> StepListDto.builder()
                        .id(step.getId())
                        .name(step.getName())
                        .applicationId(step.getApplicationId())
                        .result(step.isResult())
                        .notes(step.getNotes())
                        .build());
    }


    @PatchMapping("/updateStep/")
    public StepDto updateStep(@RequestBody StepUpdateDto dto){
        Step step=stepService.updateStep(dto.toStep());
        return StepDto.builder()
                .id(step.getId())
                .name(step.getName())
                .orderCount(step.getOrderCount())
                .applicationId(step.getApplicationId())
                .result(step.isResult())
                .notes(step.getNotes())
                .build();
    }


}

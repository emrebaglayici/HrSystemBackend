package com.emrebaglayici.myhremrebaglayici.Controllers;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IStep;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.StepDtos.StepCreateDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.StepDtos.StepDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.StepDtos.StepListDto;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.StepDtos.StepUpdateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class StepController {
    private final IStep iStep;

    public StepController(IStep iStep) {
        this.iStep = iStep;
    }

    @PostMapping("step")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody StepCreateDto dto) {
        this.iStep.createStep(dto);
    }

    @GetMapping("steps")
    public Page<StepListDto> listStep(Pageable pageable) {
        return iStep.listStep(pageable)
                .map(step -> StepListDto.builder()
                        .id(step.getId())
                        .name(step.getName())
                        .applicationId(step.getApplicationId())
                        .result(step.isResult())
                        .notes(step.getNotes())
                        .build());
    }

    @PatchMapping("/updateStep/")
    public StepDto updateStep(@RequestBody StepUpdateDto dto) {
        Step step = iStep.updateStep(dto.toStep());
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

package com.emrebaglayici.myhremrebaglayici.Controllers;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.StepService;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.StepCreateDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}

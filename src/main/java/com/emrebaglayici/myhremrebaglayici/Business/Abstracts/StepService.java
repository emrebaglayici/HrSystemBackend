package com.emrebaglayici.myhremrebaglayici.Business.Abstracts;

import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.StepCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.Step;

public interface StepService {
    void createStep(StepCreateDto step);
    Step updateStep(Step step);
}

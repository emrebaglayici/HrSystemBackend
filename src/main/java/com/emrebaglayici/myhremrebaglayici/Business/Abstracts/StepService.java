package com.emrebaglayici.myhremrebaglayici.Business.Abstracts;

import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.StepCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.Step;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StepService {
    void createStep(StepCreateDto step);
    Step updateStep(Step step);
    Page<Step> listStep(Pageable pageable);
}

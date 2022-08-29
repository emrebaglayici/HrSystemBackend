package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.ApplicationCheckService;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.StepService;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.StepCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import com.emrebaglayici.myhremrebaglayici.Entities.Steps;
import com.emrebaglayici.myhremrebaglayici.Exceptions.FillTheBlanksException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.NotFountException;
import com.emrebaglayici.myhremrebaglayici.Repository.StepRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class StepManager implements StepService {
    private final StepRepository stepRepository;
    private final ApplicationCheckManager applicationCheckManager;

    public StepManager(StepRepository stepRepository, ApplicationCheckManager applicationCheckManager) {
        this.stepRepository = stepRepository;
        this.applicationCheckManager = applicationCheckManager;
    }

    @Override
    public void createStep(StepCreateDto step) {
        if (!step.toStep().isResult())
            throw new NotFountException("Step must be true at first");
        if (step.toStep().getNotes().isEmpty() || step.toStep().getName().isEmpty())
            throw new FillTheBlanksException("Please fill all blanks");
        Optional<Application> applyOptional=this.applicationCheckManager.findById(step.toStep().getApplicationId());
        Application application=applyOptional.orElseThrow(()->new NotFountException("Application not found"));
        if (!step.toStep().getName().equals(Steps.HR.getName()))
            throw new NotFountException("Step must begin with HR");
        step.toStep().setOrderCount(1);
        this.stepRepository.save(step.toStep());
    }
}

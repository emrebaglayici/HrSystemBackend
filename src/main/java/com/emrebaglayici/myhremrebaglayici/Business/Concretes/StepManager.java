package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.JobAdvertisementCheckService;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.StepService;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.StepCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Entities.Step;
import com.emrebaglayici.myhremrebaglayici.Entities.Steps;
import com.emrebaglayici.myhremrebaglayici.Exceptions.FillTheBlanksException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.NotFountException;
import com.emrebaglayici.myhremrebaglayici.Repository.StepRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class StepManager implements StepService {
    private final StepRepository stepRepository;
    private final ApplicationCheckManager applicationCheckManager;
    private final JobAdvertisementCheckService jobAdvertisementCheckService;

    public StepManager(StepRepository stepRepository, ApplicationCheckManager applicationCheckManager, JobAdvertisementCheckService jobAdvertisementCheckService) {
        this.stepRepository = stepRepository;
        this.applicationCheckManager = applicationCheckManager;
        this.jobAdvertisementCheckService = jobAdvertisementCheckService;
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

    @Override
    public Step updateStep(Step step) {
        Optional<Step> stepOptional=this.stepRepository.findById(step.getId());
        Step steps=stepOptional.orElseThrow(()->new NotFountException("Step not found!"));

        Optional<Application> applicationOptional=this.applicationCheckManager.findById(step.getApplicationId());
        Application application=applicationOptional.orElseThrow(()->new NotFountException("Application not found"));

        Optional<JobAdvertisement> jobAdvertisementOptional=this.jobAdvertisementCheckService.getJobById(application.getJobId());
        JobAdvertisement jobAdvertisement=jobAdvertisementOptional.orElseThrow(()->new NotFountException("Job Advertisement not found"));

        if (!steps.isResult()){
            log.info("Steps are over , result :"+steps.isResult());
            throw new NotFountException("Interviewer fails");
        }
        if (steps.getName().equals(Steps.OFFER.getName()) &&
                steps.getOrderCount()<jobAdvertisement.getInterviewCount())
            throw new NotFountException("Interviewer not in last step");

        if (steps.getOrderCount()!=jobAdvertisement.getInterviewCount()){
            steps.setName(step.getName());
            steps.setResult(step.isResult());
            steps.setNotes(steps.getNotes());
            steps.setOrderCount(steps.getOrderCount()+1);
        }else{

            steps.setName(Steps.OFFER.getName());
            throw new NotFountException("Steps are over!");
        }
        this.stepRepository.save(steps);
        return steps;
    }
}

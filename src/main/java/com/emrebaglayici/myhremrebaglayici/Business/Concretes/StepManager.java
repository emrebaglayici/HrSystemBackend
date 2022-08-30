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
        Optional<Application> applyOptional = this.applicationCheckManager.findById(step.toStep().getApplicationId());
        Application application = applyOptional.orElseThrow(() -> new NotFountException("Application not found"));
        if (!step.toStep().getName().equals(Steps.HR.getName()))
            throw new NotFountException("Step must begin with HR");
        step.toStep().setOrderCount(1);
        this.stepRepository.save(step.toStep());
    }

    @Override
    public Step updateStep(Step step) {
        Optional<Step> stepOptional = this.stepRepository.findById(step.getId());
        Step steps = stepOptional.orElseThrow(() -> new NotFountException("Step not found!"));

        Optional<Application> applicationOptional = this.applicationCheckManager.findById(step.getApplicationId());
        Application application = applicationOptional.orElseThrow(() -> new NotFountException("Application not found"));

        Optional<JobAdvertisement> jobAdvertisementOptional = this.jobAdvertisementCheckService.getJobById(application.getJobId());
        JobAdvertisement jobAdvertisement = jobAdvertisementOptional.orElseThrow(() -> new NotFountException("Job Advertisement not found"));


        if (step.getName().equals(Steps.HR.getName()))
            throw new NotFountException("Hr can only in first step");
        if (steps.getOrderCount() >= jobAdvertisement.getInterviewCount())
            throw new NotFountException("Steps count reached, you cannot add any step");
        if (!steps.isResult()) {
            steps.setName(step.getName());
            steps.setResult(step.isResult());
            steps.setNotes(step.getNotes());
            steps.setOrderCount(jobAdvertisement.getInterviewCount() + 1);
            log.info("Steps are over because interview fails");
            return steps;
        }

        if (steps.getOrderCount() < jobAdvertisement.getInterviewCount() - 1) {
            log.info(steps.getOrderCount().toString());
            if (!step.getName().equals(Steps.OFFER.getName())) {
                steps.setName(step.getName());
                steps.setResult(step.isResult());
                steps.setNotes(step.getNotes());
                steps.setOrderCount(steps.getOrderCount() + 1);
                log.info(steps.getOrderCount().toString());
                this.stepRepository.save(steps);
                return steps;
            }
            if (step.getName().equals(Steps.OFFER.getName())) {
                steps.setName(step.getName());
                steps.setResult(step.isResult());
                steps.setNotes(step.getNotes());
                steps.setOrderCount(jobAdvertisement.getInterviewCount() + 1);
                log.info("Offer given to interviewer and steps are done.");
                this.stepRepository.save(steps);
                return steps;
            }

        }
        log.info(steps.getOrderCount().toString());
        if (step.getName().equals(Steps.OFFER.getName())) {
            steps.setName(step.getName());
            steps.setResult(step.isResult());
            steps.setNotes(step.getNotes());
            steps.setOrderCount(steps.getOrderCount() + 1);
            log.info(steps.getOrderCount().toString());
            this.stepRepository.save(steps);
            return steps;
        }
        if (!step.getName().equals(Steps.OFFER.getName()))
            throw new NotFountException("Offer must be last step");
        return steps;
    }
}

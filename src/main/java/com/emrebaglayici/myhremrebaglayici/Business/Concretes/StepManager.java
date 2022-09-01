package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.JobAdvertisementCheckService;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.StepService;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dto.StepCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Entities.Step;
import com.emrebaglayici.myhremrebaglayici.Entities.Steps;
import com.emrebaglayici.myhremrebaglayici.Exceptions.FillTheBlanksException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.InterviewFailException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.NotFountException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.PermissionException;
import com.emrebaglayici.myhremrebaglayici.Helper.Helper;
import com.emrebaglayici.myhremrebaglayici.Repository.StepRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            throw new NotFountException(Helper.STEP_MUST_BE_TRUE_AT_FIRST);
        if (step.toStep().getNotes().isEmpty() || step.toStep().getName().isEmpty())
            throw new FillTheBlanksException(Helper.FILL_ALL_BLANKS);
        Optional<Application> applyOptional = this.applicationCheckManager.findById(step.toStep().getApplicationId());
        Application application = applyOptional.orElseThrow(() -> new NotFountException(Helper.APPLICATION_NOT_FOUND));
        if (!step.toStep().getName().equals(Steps.HR.getName()))
            throw new NotFountException(Helper.FIRST_STEP_MUST_BE_HR);
        step.toStep().setOrderCount(1);
        this.stepRepository.save(step.toStep());
    }

    @Override
    public Step updateStep(Step step) {
        Optional<Step> stepOptional = this.stepRepository.findById(step.getId());
        Step steps = stepOptional.orElseThrow(() -> new NotFountException(Helper.STEP_NOT_FOUND));

        Optional<Application> applicationOptional = this.applicationCheckManager.findById(step.getApplicationId());
        Application application = applicationOptional.orElseThrow(() -> new NotFountException(Helper.APPLICATION_NOT_FOUND));

        Optional<JobAdvertisement> jobAdvertisementOptional = this.jobAdvertisementCheckService.getJobById(application.getJobId());
        JobAdvertisement jobAdvertisement = jobAdvertisementOptional.orElseThrow(() -> new NotFountException(Helper.JOB_ADVERTISEMENT_NOT_FOUND));
        boolean isNameOffer = step.getName().equals(Steps.OFFER.getName());
        if (step.getName().equals(Steps.HR.getName()))
            throw new PermissionException(Helper.HR_CAN_ONLY_FIRST_STEP);
        if (steps.getOrderCount() >= jobAdvertisement.getInterviewCount())
            throw new PermissionException(Helper.STEP_COUNT_REACHED);
        if (!steps.isResult()) {
            steps.setName(step.getName());
            steps.setResult(step.isResult());
            steps.setNotes(step.getNotes());
            steps.setOrderCount(jobAdvertisement.getInterviewCount() + 1);
            log.info("Steps are over because interview fails");
            throw new InterviewFailException(Helper.STEP_COUNT_REACHED);
        }

        if (steps.getOrderCount() < jobAdvertisement.getInterviewCount() - 1) {
            steps.setName(step.getName());
            steps.setResult(step.isResult());
            steps.setNotes(step.getNotes());
            if (!isNameOffer) {
                steps.setOrderCount(steps.getOrderCount() + 1);
            } else {
                steps.setOrderCount(jobAdvertisement.getInterviewCount() + 1);
                log.info("Offer given to interviewer and steps are done.");
            }
            log.info("Step continue with success : " + steps);
            this.stepRepository.save(steps);
            return steps;
        }
        if (isNameOffer) {
            steps.setName(step.getName());
            steps.setResult(step.isResult());
            steps.setNotes(step.getNotes());
            steps.setOrderCount(steps.getOrderCount() + 1);
            log.info("Steps are done : " + steps);
            this.stepRepository.save(steps);
            return steps;
        }
        throw new PermissionException(Helper.OFFER_MUST_BE_LAST_STEP);
    }

    @Override
    public Page<Step> listStep(Pageable pageable) {
        return this.stepRepository.findAll(pageable);
    }
}

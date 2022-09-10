package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IJobAdvertisementCheck;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IStep;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.StepDtos.StepCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Entities.Step;
import com.emrebaglayici.myhremrebaglayici.Entities.Steps;
import com.emrebaglayici.myhremrebaglayici.Exceptions.FillTheBlanksException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.InterviewFailException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.NotFoundException;
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
public class StepManager implements IStep {
    private final StepRepository stepRepository;
    private final ApplicationCheckManager applicationCheckManager;
    private final IJobAdvertisementCheck iJobAdvertisementCheck;

    public StepManager(StepRepository stepRepository, ApplicationCheckManager applicationCheckManager, IJobAdvertisementCheck iJobAdvertisementCheck) {
        this.stepRepository = stepRepository;
        this.applicationCheckManager = applicationCheckManager;
        this.iJobAdvertisementCheck = iJobAdvertisementCheck;
    }

    @Override
    public void createStep(StepCreateDto step) {
        //Creating interview step.
        //Rules-> Step result must be true at first and first step must be Hr.
        if (!step.toStep().isResult()){
            log.info("Step must be true at first.");
            throw new NotFoundException(Helper.STEP_MUST_BE_TRUE_AT_FIRST);
        }
        if (step.toStep().getNotes().isEmpty() || step.toStep().getName().isEmpty()){
            log.info("Step notes or name empty : "+ step.toStep().getNotes()+" "+step.toStep().getName());
            throw new FillTheBlanksException(Helper.FILL_ALL_BLANKS);
        }
        Optional<Application> applyOptional = this.applicationCheckManager.findById(step.toStep().getApplicationId());
        Application application = applyOptional.orElseThrow(() -> new NotFoundException(Helper.APPLICATION_NOT_FOUND));
        if (!step.toStep().getName().equals(Steps.HR.getName())){
            log.info("First step must be Hr.");
            throw new NotFoundException(Helper.FIRST_STEP_MUST_BE_HR);
        }
        log.info("Step created successfully");
        step.toStep().setOrderCount(1);
        this.stepRepository.save(step.toStep());
    }

    @Override
    public Step updateStep(Step step) {
        //Step updating algorithm
        // Rules -> Hr can be only first step , if step count reached then step is over
        // if step result is before step count reached false then interview fails , if step count reached then steps are over,
        // Offer can be given any step.
        Optional<Step> stepOptional = this.stepRepository.findById(step.getId());
        Step steps = stepOptional.orElseThrow(() -> new NotFoundException(Helper.STEP_NOT_FOUND));

        Optional<Application> applicationOptional = this.applicationCheckManager.findById(step.getApplicationId());
        Application application = applicationOptional.orElseThrow(() -> new NotFoundException(Helper.APPLICATION_NOT_FOUND));

        Optional<JobAdvertisement> jobAdvertisementOptional = this.iJobAdvertisementCheck.getJobById(application.getJobId());
        JobAdvertisement jobAdvertisement = jobAdvertisementOptional.orElseThrow(() -> new NotFoundException(Helper.JOB_ADVERTISEMENT_NOT_FOUND));
        boolean isNameOffer = step.getName().equals(Steps.OFFER.getName());
        if (step.getName().equals(Steps.HR.getName())){
            log.info("Hr must be only first step");
            throw new PermissionException(Helper.HR_CAN_ONLY_FIRST_STEP);
        }
        if (steps.getOrderCount() >= jobAdvertisement.getInterviewCount()){
            log.info("Step count reached , steps are over -> step count : "+steps.getOrderCount());
            throw new PermissionException(Helper.STEP_COUNT_REACHED);
        }
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
        log.info("Offer must be last step");
        throw new PermissionException(Helper.OFFER_MUST_BE_LAST_STEP);
    }

    @Override
    public Page<Step> listStep(Pageable pageable) {
        //Lists all steps
        return this.stepRepository.findAll(pageable);
    }
}

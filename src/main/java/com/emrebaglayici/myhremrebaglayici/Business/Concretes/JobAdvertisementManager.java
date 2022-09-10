package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IJobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.IUserCheck;
import com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.JobAdsDtos.JobAdvertisementCreateDto;
import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import com.emrebaglayici.myhremrebaglayici.Entities.Role;
import com.emrebaglayici.myhremrebaglayici.Entities.User;
import com.emrebaglayici.myhremrebaglayici.Exceptions.FillTheBlanksException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.NotFoundException;
import com.emrebaglayici.myhremrebaglayici.Exceptions.PermissionException;
import com.emrebaglayici.myhremrebaglayici.Helper.Helper;
import com.emrebaglayici.myhremrebaglayici.Repository.JobAdvertisementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class JobAdvertisementManager implements IJobAdvertisement {

    private final JobAdvertisementRepository jobAdvertisementRepository;
    private final IUserCheck iUserCheck;

    public JobAdvertisementManager(JobAdvertisementRepository jobAdvertisementRepository,
                                   IUserCheck iUserCheck) {
        this.jobAdvertisementRepository = jobAdvertisementRepository;
        this.iUserCheck = iUserCheck;
    }

    @Override
    public Optional<JobAdvertisement> findById(Long id) {
        //Finds job advertisement by id.
        return this.jobAdvertisementRepository.findById(id);
    }

    @Override
    public void addJobAds(JobAdvertisementCreateDto dto) {
        //Create job advertisement
        //Rules -> User must be HR , inteview count must 1 to 5
        Optional<User> userOptional = iUserCheck.getUserById(dto.toJobAds().getUserId());
        userOptional.orElseThrow(() -> new NotFoundException(Helper.USER_NOT_FOUND));

        if (!userOptional.get().getRole().equals(Role.HR.getName())) {
            log.info("User with id : " + userOptional.get().getId() + " is not Hr.");
            throw new PermissionException(Helper.USER_MUST_BE_HR);
        }

        if (dto.toJobAds().getInterviewCount() > 5 || dto.toJobAds().getInterviewCount() == 0) {
            log.info("Interview count must be 1 to 5 -> current : " + dto.toJobAds().getInterviewCount());
            throw new NotFoundException(Helper.INTERVIEW_COUNT_MUST_BE_1TO5);
        }
        if (!dto.toJobAds().getType().equals("") &&
                dto.toJobAds().getSalary() != 0 && !dto.toJobAds().getDescription().equals("")) {
            log.info("Job advertisement added successfully : " + dto.toJobAds());
            this.jobAdvertisementRepository.save(dto.toJobAds());
        } else
            throw new FillTheBlanksException(Helper.FILL_ALL_BLANKS);
    }

    @Override
    public Page<JobAdvertisement> listJobAds(Pageable pageable) {
        //List all job advertisements.
        return this.jobAdvertisementRepository.findAll(pageable);
    }

    @Override
    public void update(Long id, Long userId, JobAdvertisement jobAdvertisement) {
        //Updates the job advertisement
        // Rule -> only person who can update this advertisement is created user.
        Optional<User> userOptional = iUserCheck.getUserById(userId);
        userOptional.orElseThrow(() -> new NotFoundException(Helper.USER_NOT_FOUND));
        if (!Objects.equals(jobAdvertisement.getUserId(), userId)) {
            log.info("Created user update this advertisement");
            throw new PermissionException("Only person who can update this advertisement is created user");
        }
        log.info("Job advertisement updated successfully");
        this.jobAdvertisementRepository.save(jobAdvertisement);
    }

    @Override
    public JobAdvertisement deleteById(Long id, Long userId) {
        //Delete job advertisement by id.
        //Rule -> Only Hr delete.
        Optional<JobAdvertisement> jobAdsOptional = this.jobAdvertisementRepository.findById(id);
        JobAdvertisement jobAds = jobAdsOptional.orElseThrow(() -> new NotFoundException(Helper.JOB_ADVERTISEMENT_NOT_FOUND));
        Optional<User> userOptional = this.iUserCheck.getUserById(userId);
        User user = userOptional.orElseThrow(() -> new NotFoundException(Helper.USER_NOT_FOUND));
        if (!userOptional.get().getRole().equals(Role.HR.getName())) {
            log.info("User with id : " + userOptional.get().getId() + " is not Hr.");
            throw new NotFoundException(Helper.USER_MUST_BE_HR);
        }
        log.info("Job advertisement deleted successfully : " + jobAds);
        this.jobAdvertisementRepository.delete(jobAds);
        return jobAds;
    }


}

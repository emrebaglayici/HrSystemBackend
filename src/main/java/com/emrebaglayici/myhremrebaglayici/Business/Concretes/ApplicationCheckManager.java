package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.ApplicationCheckService;
import com.emrebaglayici.myhremrebaglayici.Entities.Application;
import com.emrebaglayici.myhremrebaglayici.Repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationCheckManager implements ApplicationCheckService {
    private final ApplicationRepository applicationRepository;

    public ApplicationCheckManager(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Optional<Application> findById(Long id) {
        return applicationRepository.findById(id);
    }

}

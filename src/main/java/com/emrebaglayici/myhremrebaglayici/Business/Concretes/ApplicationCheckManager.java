package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.ApplicationCheckService;
import com.emrebaglayici.myhremrebaglayici.Repository.ApplicationRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplicationCheckManager implements ApplicationCheckService {
    private final ApplicationRepository applicationRepository;

    public ApplicationCheckManager(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public boolean setJobAdsActive(Long id) {
        return true;
    }
}

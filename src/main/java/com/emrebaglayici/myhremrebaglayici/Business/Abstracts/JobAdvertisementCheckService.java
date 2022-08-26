package com.emrebaglayici.myhremrebaglayici.Business.Abstracts;

public interface JobAdvertisementCheckService {
    boolean existsJob(Long id);

    long count();
}

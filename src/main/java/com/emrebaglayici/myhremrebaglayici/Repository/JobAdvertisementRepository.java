package com.emrebaglayici.myhremrebaglayici.Repository;

import com.emrebaglayici.myhremrebaglayici.Entities.JobAdvertisement;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JobAdvertisementRepository extends PagingAndSortingRepository<JobAdvertisement,Long> {
}

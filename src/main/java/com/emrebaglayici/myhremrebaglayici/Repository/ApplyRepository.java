package com.emrebaglayici.myhremrebaglayici.Repository;

import com.emrebaglayici.myhremrebaglayici.Entities.Apply;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ApplyRepository extends PagingAndSortingRepository<Apply,Long> {

    @Query("SELECT userId FROM Apply WHERE jobId =:jobId")
    Long getUserIdByJobId(Long jobId);

    @Query("SELECT userId FROM Apply WHERE id =:id")
    Long getUserIdById(Long id);

}

package com.emrebaglayici.myhremrebaglayici.Business.Abstracts;

import com.emrebaglayici.myhremrebaglayici.Core.DataResult;
import com.emrebaglayici.myhremrebaglayici.Core.Result;
import com.emrebaglayici.myhremrebaglayici.Core.SuccessDataResult;
import com.emrebaglayici.myhremrebaglayici.Entities.Concretes.Candidates;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CandidatesService {
    Result add(Candidates candidate);

    @Query("DELETE FROM Candidates WHERE id = :id")
    Result deleteById(@Param("id") Integer id);

    DataResult<List<Candidates>> getAll();

    Result setName(Integer id,String name);

}

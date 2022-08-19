package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.CandidatesService;
import com.emrebaglayici.myhremrebaglayici.Core.Result;
import com.emrebaglayici.myhremrebaglayici.Core.SuccessDataResult;
import com.emrebaglayici.myhremrebaglayici.DateAccess.CandidatesDao;
import com.emrebaglayici.myhremrebaglayici.Entities.Concretes.Candidates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidatesManager implements CandidatesService {

    private CandidatesDao candidatesDao;

    @Autowired
    public CandidatesManager(CandidatesDao candidatesDao) {
        this.candidatesDao = candidatesDao;
    }

    @Override
    public Result add(Candidates candidate) {
        this.candidatesDao.save(candidate);
        return new SuccessDataResult<>("Candidates added");
    }
}

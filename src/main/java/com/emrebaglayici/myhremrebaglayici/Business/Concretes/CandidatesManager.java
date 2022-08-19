package com.emrebaglayici.myhremrebaglayici.Business.Concretes;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.CandidatesService;
import com.emrebaglayici.myhremrebaglayici.Core.DataResult;
import com.emrebaglayici.myhremrebaglayici.Core.Result;
import com.emrebaglayici.myhremrebaglayici.Core.SuccessDataResult;
import com.emrebaglayici.myhremrebaglayici.Core.SuccessResult;
import com.emrebaglayici.myhremrebaglayici.DateAccess.CandidatesDao;
import com.emrebaglayici.myhremrebaglayici.Entities.Concretes.Candidates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Result deleteById(Integer id) {
        this.candidatesDao.deleteById(id);
        return new SuccessDataResult<>("Candidate deleted");
    }

    @Override
    public DataResult<List<Candidates>> getAll() {
        return new SuccessDataResult<List<Candidates>>(
                this.candidatesDao.findAll(),"Candidates are listed"
        );
    }

    @Override
    public Result setName(Integer id,String name) {
        Candidates candidates;
        candidates=this.candidatesDao.findById(id).get();
        candidates.setName(name);
        this.candidatesDao.save(candidates);
        return new SuccessResult("Candidates name changed");
    }

}

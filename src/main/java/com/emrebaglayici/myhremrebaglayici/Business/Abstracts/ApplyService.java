package com.emrebaglayici.myhremrebaglayici.Business.Abstracts;

import com.emrebaglayici.myhremrebaglayici.Core.DataResult;
import com.emrebaglayici.myhremrebaglayici.Core.Result;
import com.emrebaglayici.myhremrebaglayici.Entities.Apply;
import org.springframework.data.jpa.repository.Query;

public interface ApplyService {

    Apply applyJob(Apply apply);
}
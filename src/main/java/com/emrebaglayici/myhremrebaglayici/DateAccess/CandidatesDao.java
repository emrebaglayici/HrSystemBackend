package com.emrebaglayici.myhremrebaglayici.DateAccess;

import com.emrebaglayici.myhremrebaglayici.Core.Result;
import com.emrebaglayici.myhremrebaglayici.Entities.Concretes.Candidates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CandidatesDao extends JpaRepository<Candidates,Integer> {


}
